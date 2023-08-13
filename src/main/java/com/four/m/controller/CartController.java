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

    @ApiOperation("购物车列表")
    @GetMapping("/list")
    public ApiRestResponse list() {
        // 内部获取用户ID，防止横向越权
        List<CartVo> cartList = cartService.list (UserFilter.currentUser.getId ());
        return ApiRestResponse.success (cartList);
    }

    @ApiOperation("添加商品到购物车")
    @PostMapping("/add")
    public ApiRestResponse add(@RequestParam Integer productId, @RequestParam Integer count) {

        List<CartVo> cartVoList = cartService.add (UserFilter.currentUser.getId (), productId, count);
        return ApiRestResponse.success (cartVoList);
    }

    @ApiOperation("更新购物车")
    @PostMapping("/update")
    public ApiRestResponse update(@RequestParam Integer productId, @RequestParam Integer count) {

        List<CartVo> cartVoList = cartService.update (UserFilter.currentUser.getId (), productId, count);
        return ApiRestResponse.success (cartVoList);
    }

    @ApiOperation("删除购物车")
    @PostMapping("/delete")
    public ApiRestResponse delete(@RequestParam Integer productId) {
        // 不能传用户ID与购物车ID，会被利用删除别人到购物车
        List<CartVo> cartVoList = cartService.delete (UserFilter.currentUser.getId (), productId);
        return ApiRestResponse.success (cartVoList);
    }
}
