package com.four.m.service;

import com.four.m.domain.Product;
import com.four.m.model.request.AddProductReq;

/**
 * 描述：     商品Service
 */
public interface ProductService {

    void add(AddProductReq addProductReq);

    void update(Product updateProduct);

    void delete(Integer id);
}
