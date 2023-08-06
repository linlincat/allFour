package com.four.m.controller;

/*
* 描述:         前台商品Controller
* */

import com.four.m.common.ApiRestResponse;
import com.four.m.domain.Product;
import com.four.m.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

@ApiOperation ("商品详情")
@GetMapping("product/detail")
    public ApiRestResponse detail(@RequestParam Integer id) {
    Product product = productService.detail (id);
    return ApiRestResponse.success (product);

}
}
