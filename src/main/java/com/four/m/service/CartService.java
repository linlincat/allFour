package com.four.m.service;

/*
* 描述:      购物车 Service
* */


import com.four.m.model.vo.CartVo;

import java.util.List;

public interface CartService {

    List<CartVo> list(Integer userId);

    List<CartVo> add(Integer userId, Integer productId, Integer count);

    List<CartVo> update(Integer userId, Integer productId, Integer count);

    List<CartVo> delete(Integer userId, Integer productId);

    List<CartVo> selectOrNot(Integer userId, Integer productId, Integer selected);

    List<CartVo> selectAllOrNot(Integer userId, Integer selected);
}
