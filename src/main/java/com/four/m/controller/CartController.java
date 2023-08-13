package com.four.m.controller;

import com.four.m.common.ApiRestResponse;
import com.four.m.filter.UserFilter;
import com.four.m.model.vo.CartVo;
import com.four.m.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * 描述：        购物车
 * */
@Controller
@ResponseBody  // 不添加返回 提示查找不到路径
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @ApiOperation ("添加商品到购物车")
    @PostMapping("/add")
    public ApiRestResponse add(@RequestParam Integer productId, @RequestParam Integer count) {

        cartService.add (UserFilter.currentUser.getId (), productId, count);
        return ApiRestResponse.success ();
    }
}
