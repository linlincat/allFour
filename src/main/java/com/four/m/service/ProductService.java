package com.four.m.service;

import com.four.m.domain.Product;
import com.four.m.model.request.AddProductReq;
import com.four.m.model.request.ProductListReq;
import com.github.pagehelper.PageInfo;

/**
 * 描述：     商品Service
 */
public interface ProductService {

    void add(AddProductReq addProductReq);

    void update(Product updateProduct);

    void delete(Integer id);

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    Product detail(Integer id);

    PageInfo list(ProductListReq productListReq);
}
