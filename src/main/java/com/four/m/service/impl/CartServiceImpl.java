package com.four.m.service.impl;

import com.four.m.common.Constant;
import com.four.m.domain.Cart;
import com.four.m.domain.Product;
import com.four.m.exception.FourException;
import com.four.m.exception.FourExceptionEnum;
import com.four.m.mapper.CartMapper;
import com.four.m.mapper.ProductMapper;
import com.four.m.model.vo.CartVo;
import com.four.m.service.CartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/*
 * 描述:         购物车service实现类
 * */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CartMapper cartMapper;


    @Override
    public List<CartVo> list(Integer userId) {
        List<CartVo> cartVos = cartMapper.selectList (userId);
        for (int i = 0; i < cartVos.size (); i++) {
            CartVo cartVo = cartVos.get (i);
            cartVo.setTotalPrice (cartVo.getPrice () * cartVo.getQuantity ());
        }
        return  cartVos;
    }

    @Override
    public List<CartVo> add(Integer userId, Integer productId, Integer count) {
        validProduct(productId, count);

        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里，需要新增一个记录
            // 不添加类型Cart表示新建一个商品赋值给上面定义的购物车，添加表示新创建一个，这个我们是需要赋值
            cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setQuantity(count);
            cart.setSelected(Constant.Cart.SELECTED);
            cartMapper.insertSelective(cart);
        } else {
            //这个商品已经在购物车里了，则数量相加
            count = cart.getQuantity() + count;
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.SELECTED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }


    private void validProduct(Integer productId, Integer count) {
        Product product = productMapper.selectByPrimaryKey (productId);

        // 判断商品是否存在，商品是否上架
        if (product == null || product.getStatus ().equals (Constant.SaleStatus.NOT_SALE)) {
            throw new FourException (FourExceptionEnum.NOT_SALE);
        }
        // 判断商品库存
        if (count > product.getStock ()) {
            throw new FourException (FourExceptionEnum.NOT_ENOUGH);
        }
    }
}
