package com.four.m.controller;

import com.four.m.common.ApiRestResponse;
import com.four.m.domain.Order;
import com.four.m.model.request.OrderReq;
import com.four.m.model.vo.OrderVO;
import com.four.m.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
 * 描述：       订单Controller
 * */
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("order/create")
    @ApiOperation("创建订单")
    public ApiRestResponse create(@RequestBody OrderReq orderReq) {
        String orderNo = orderService.create (orderReq);
        // 成功后 - 返回前端订单号
        return ApiRestResponse.success (orderNo);
    }

    @GetMapping("order/detail")
    @ApiOperation("前台订单详情")
    public ApiRestResponse detail(@RequestParam String orderNo) {
        OrderVO orderVO = orderService.detail (orderNo);
        // 成功后 - 返回前端订单号
        return ApiRestResponse.success (orderVO);
    }

    @PostMapping("order/cancel")
    @ApiOperation("前台取消订单")
    public ApiRestResponse cancel(@RequestParam String orderNo) {
        orderService.cancel (orderNo);
        return ApiRestResponse.success ();
    }
}
