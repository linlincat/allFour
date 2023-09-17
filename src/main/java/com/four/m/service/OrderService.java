package com.four.m.service;

import com.four.m.model.request.OrderReq;
import com.four.m.model.vo.OrderVO;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

/**
 * 描述：     订单Service
 */
@Service
public interface OrderService {
    String create(OrderReq orderReq);

    //    @Override
    //    public OrderVO detail() {
    //        return detail ();
    //    }
    //
    OrderVO detail(String orderNo);

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
    void cancel(String orderNo);
//
//    com.imooc.mall.model.vo.OrderVO detail(String orderNo);
//
//    PageInfo listForCustomer(Integer pageNum, Integer pageSize);
//
//    void cancel(String orderNo);
//
//    String qrcode(String orderNo);
//
//    void pay(String orderNo);
//
//    PageInfo listForAdmin(Integer pageNum, Integer pageSize);
//
//    void deliver(String orderNo);
//
//    void finish(String orderNo);
}
