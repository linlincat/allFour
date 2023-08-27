package com.four.m.service;

import com.four.m.model.request.OrderReq;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

/**
 * 描述：     订单Service
 */
@Service
public interface OrderService {
    String create(OrderReq orderReq);
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
