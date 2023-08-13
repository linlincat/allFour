package com.four.m.service;

/*
* 描述:      购物车 Service
* */


import com.four.m.model.vo.CartVo;

import java.util.List;

public interface CartService {

    List<CartVo> add(Integer userId, Integer productId, Integer count);
}
