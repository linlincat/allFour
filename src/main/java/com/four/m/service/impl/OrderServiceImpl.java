package com.four.m.service.impl;

import com.four.m.common.Constant;
import com.four.m.domain.Order;
import com.four.m.domain.OrderItem;
import com.four.m.domain.Product;
import com.four.m.exception.FourException;
import com.four.m.exception.FourExceptionEnum;
import com.four.m.filter.UserFilter;
import com.four.m.mapper.CartMapper;
import com.four.m.mapper.OrderItemMapper;
import com.four.m.mapper.OrderMapper;
import com.four.m.mapper.ProductMapper;
import com.four.m.model.request.OrderReq;
import com.four.m.model.vo.CartVo;
import com.four.m.model.vo.OrderVO;
import com.four.m.service.CartService;
import com.four.m.service.OrderService;

import java.util.ArrayList;
import java.util.List;

import com.four.m.utils.OrderCodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 描述：     订单Service实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    CartService cartService;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Override
    public String create(OrderReq orderReq) {
        //拿到用户ID
        Integer userId = UserFilter.currentUser.getId ();
        //从购物车查找已经勾选的商品
        List<CartVo> cartVoList = cartService.list (userId);
        ArrayList<CartVo> cartVoArrayListTemp = new ArrayList<> ();
        for (int i = 0; i < cartVoList.size (); i++) {
            CartVo cartVo = cartVoList.get (i);
            if (cartVo.getSelected ().equals (Constant.Cart.SELECTED)) {
                cartVoArrayListTemp.add (cartVo);
            }
        }
        cartVoList = cartVoArrayListTemp;
        //如果购物车已勾选的为空，报错
        if(CollectionUtils.isEmpty (cartVoList)) {
            throw new FourException (FourExceptionEnum.CART_EMPTY);
        }
        //从购物车查找已经勾选的商品
//        判断商品是否存在、上下架状态、库存
        validSaleStatusAndStock(cartVoList);
//        把购物车对象转为订单item对象
        List<OrderItem> orderItemList = cartVoListToOrderItemList (cartVoList);
        //扣库存
        for (int i = 0; i < orderItemList.size (); i++) {
            OrderItem orderItem = orderItemList.get (i);
            Product product = productMapper.selectByPrimaryKey (orderItem.getProductId ());
            int stock = product.getStock () - orderItem.getQuantity ();
            if(stock < 0) {
                throw new FourException (FourExceptionEnum.NOT_ENOUGH);
            }
            product.setStock (stock);
            productMapper.updateByPrimaryKeySelective (product);
        }
        //把购物车中的已勾选商品删除
        clearCart(cartVoList);
        //生成订单
        Order order = new Order ();
        //生成订单号，有独立的规则
        String orderNo = OrderCodeFactory.getOrderCode (Long.valueOf (userId));
        order.setOrderNo (orderNo);
        order.setUserId (userId);
        order.setTotalPrice (totalPrice(orderItemList));
        order.setReceiverName (orderReq.getReceiverName ());
        order.setReceiverAddress (orderReq.getReceiverAddress ());
        order.setReceiverMobile (orderReq.getReceiverMobile ());
        order.setOrderStatus (Constant.OrderStatusEnum.NOT_PAID.getCode ());
        order.setPostage (0);
        order.setPaymentType (1);
        //插入到Order表
        orderMapper.insertSelective (order);
        //循环保存每个商品到order_item表
        for (int i = 0; i < orderItemList.size (); i++) {
            OrderItem orderItem = orderItemList.get (i);
            orderItem.setOrderNo (order.getOrderNo ());
            orderItemMapper.insertSelective (orderItem);
        }
        // 返回结果
        return orderNo;
    }

    private Integer totalPrice(List<OrderItem> orderItemList) {
        Integer totalPrice = 0;
        for (int i = 0; i < orderItemList.size (); i++) {
            OrderItem orderItem = orderItemList.get (i);
            totalPrice += orderItem.getTotalPrice ();
        }
        return  totalPrice;
    }

    private void clearCart(List<CartVo> cartVoList) {
        for (int i = 0; i < cartVoList.size (); i++) {
            CartVo cartVo = cartVoList.get (i);
            cartMapper.deleteByPrimaryKey (cartVo.getId ());
        }
    }

    private List<OrderItem> cartVoListToOrderItemList(List<CartVo> cartVoList) {
        List<OrderItem> orderItemList = new ArrayList<> ();
        for (int i = 0; i < cartVoList.size (); i++) {
            CartVo cartVo = cartVoList.get (i);
            OrderItem orderItem = new OrderItem ();
            orderItem.setProductId (cartVo.getProductId ());
            // 记录商品快照信息 保存原始记录
            orderItem.setProductName (cartVo.getProductName ());
            orderItem.setProductImg (cartVo.getProductImage ());
            orderItem.setUnitPrice (cartVo.getPrice ());
            orderItem.setQuantity (cartVo.getQuantity ());
            orderItem.setTotalPrice (cartVo.getTotalPrice ());

            orderItemList.add (orderItem);
        }
        return  orderItemList;
    }

    private  void validSaleStatusAndStock(List<CartVo> cartVoList) {
        for (int i = 0; i < cartVoList.size (); i++) {
            CartVo cartVo = cartVoList.get (i);
            Product product = productMapper.selectByPrimaryKey (cartVo.getProductId ());

            // 判断商品是否存在，商品是否上架
            if (product == null || product.getStatus ().equals (Constant.SaleStatus.NOT_SALE)) {
                throw new FourException (FourExceptionEnum.NOT_SALE);
            }
            // 判断商品库存
            if (cartVo.getQuantity () > product.getStock ()) {
                throw new FourException (FourExceptionEnum.NOT_ENOUGH);
            }
        }

    }

//
//    //数据库事务
//    @Transactional(rollbackFor = Exception.class)
//    @Override
//
//    private void validSaleStatusAndStock(List<CartVo> cartVOList) {
//        for (int i = 0; i < cartVOList.size(); i++) {
//            CartVo cartVO = cartVOList.get(i);
//            Product product = productMapper.selectByPrimaryKey(cartVO.getProductId());
//            //判断商品是否存在，商品是否上架
//            if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)) {
//                throw new FourException (FourExceptionEnum.NOT_SALE);
//            }
//            //判断商品库存
//            if (cartVO.getQuantity() > product.getStock()) {
//                throw new FourException (FourExceptionEnum.NOT_ENOUGH);
//            }
//        }
//    }
//
//    @Override
//    public OrderVO detail() {
//        return detail ();
//    }
//
//    @Override
//    public OrderVO detail(String orderNo) {
//        Order order = orderMapper.selectByOrderNo(orderNo);
//        //订单不存在，则报错
//        if (order == null) {
//            throw new FourException (FourExceptionEnum.NO_ORDER);
//        }
//        //订单存在，需要判断所属
//        Integer userId = UserFilter.currentUser.getId();
//        if (!order.getUserId().equals(userId)) {
//            throw new FourException (FourExceptionEnum.NOT_YOUR_ORDER);
//        }
//           OrderVO orderVO = getOrderVO(order);
//        return orderVO;
//    }
//
//    private OrderVO getOrderVO(Order order) {
//           OrderVO orderVO = new OrderVO ();
//        BeanUtils.copyProperties(order, orderVO);
//        //获取订单对应的orderItemVOList
//        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(order.getOrderNo());
//        List<OrderItemVO> orderItemVOList = new ArrayList<>();
//        for (int i = 0; i < orderItemList.size(); i++) {
//            OrderItem orderItem = orderItemList.get(i);
//            OrderItemVO orderItemVO = new OrderItemVO ();
//            BeanUtils.copyProperties(orderItem, orderItemVO);
//            orderItemVOList.add(orderItemVO);
//        }
//        orderVO.setOrderItemVOList(orderItemVOList);
//        orderVO.setOrderStatusName(OrderStatusEnum.codeOf(orderVO.getOrderStatus()).getValue());
//        return orderVO;
//    }
//
//    @Override
//    public PageInfo listForCustomer(Integer pageNum, Integer pageSize) {
//        Integer userId = UserFilter.currentUser.getId();
//        PageHelper.startPage(pageNum, pageSize);
//        List<Order> orderList = orderMapper.selectForCustomer(userId);
//        List<OrderVO> orderVOList = orderListToOrderVOList(orderList);
//        PageInfo pageInfo = new PageInfo<>(orderList);
//        pageInfo.setList(orderVOList);
//        return pageInfo;
//    }
//
//    @Override
//    public void cancel(String orderNo) {
//        Order order = orderMapper.selectByOrderNo(orderNo);
//        //查不到订单，报错
//        if (order == null) {
//            throw new ImoocMallException(ImoocMallExceptionEnum.NO_ORDER);
//        }
//        //验证用户身份
//        //订单存在，需要判断所属
//        Integer userId = UserFilter.currentUser.getId();
//        if (!order.getUserId().equals(userId)) {
//            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_YOUR_ORDER);
//        }
//        if (order.getOrderStatus().equals(OrderStatusEnum.NOT_PAID.getCode())) {
//            order.setOrderStatus(OrderStatusEnum.CANCELED.getCode());
//            order.setEndTime(new Date());
//            orderMapper.updateByPrimaryKeySelective(order);
//        } else {
//            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_ORDER_STATUS);
//        }
//    }
//
//    @Override
//    public String qrcode(String orderNo) {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
//                .getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//
//        String address = ip + ":" + request.getLocalPort();
//        String payUrl = "http://" + address + "/pay?orderNo=" + orderNo;
//        try {
//            QRCodeGenerator
//                    .generateQRCodeImage(payUrl, 350, 350,
//                            Constant.FILE_UPLOAD_DIR + orderNo + ".png");
//        } catch (WriterException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String pngAddress = "http://" + address + "/images/" + orderNo + ".png";
//        return pngAddress;
//    }
//
//    @Override
//    public void pay(String orderNo) {
//        Order order = orderMapper.selectByOrderNo(orderNo);
//        //查不到订单，报错
//        if (order == null) {
//            throw new FourException (FourExceptionEnum.NO_ORDER);
//        }
//        if (order.getOrderStatus() == OrderStatusEnum.NOT_PAID.getCode()) {
//            order.setOrderStatus(OrderStatusEnum.PAID.getCode());
//            order.setPayTime(new Date());
//            orderMapper.updateByPrimaryKeySelective(order);
//        } else {
//            throw new FourException (FourExceptionEnum.WRONG_ORDER_STATUS);
//        }
//    }
//
//    @Override
//    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<Order> orderList = orderMapper.selectAllForAdmin();
//        List<OrderVO> orderVOList = orderListToOrderVOList(orderList);
//        PageInfo pageInfo = new PageInfo<>(orderList);
//        pageInfo.setList(orderVOList);
//        return pageInfo;
//    }
//
//    //发货
//    @Override
//    public void deliver(String orderNo) {
//        Order order = orderMapper.selectByOrderNo(orderNo);
//        //查不到订单，报错
//        if (order == null) {
//            throw new ImoocMallException(ImoocMallExceptionEnum.NO_ORDER);
//        }
//        if (order.getOrderStatus() == OrderStatusEnum.PAID.getCode()) {
//            order.setOrderStatus(OrderStatusEnum.DELIVERED.getCode());
//            order.setDeliveryTime(new Date());
//            orderMapper.updateByPrimaryKeySelective(order);
//        } else {
//            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_ORDER_STATUS);
//        }
//    }
//
//    @Override
//    public void finish(String orderNo) {
//        Order order = orderMapper.selectByOrderNo(orderNo);
//        //查不到订单，报错
//        if (order == null) {
//            throw new FourException (ImoocMallExceptionEnum.NO_ORDER);
//        }
//        //如果是普通用户，就要校验订单的所属
//        if (!userService.checkAdminRole(UserFilter.currentUser) && !order.getUserId().equals(UserFilter.currentUser.getId())) {
//            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_YOUR_ORDER);
//        }
//        //发货后可以完结订单
//        if (order.getOrderStatus() == OrderStatusEnum.DELIVERED.getCode()) {
//            order.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
//            order.setEndTime(new Date());
//            orderMapper.updateByPrimaryKeySelective(order);
//        } else {
//            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_ORDER_STATUS);
//        }
//    }
}
