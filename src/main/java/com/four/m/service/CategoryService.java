package com.four.m.service;

/*
* 描述:      分类目录 Service
* */

import com.four.m.model.request.AddCategoryReq;

public interface CategoryService {
    void add(AddCategoryReq addCategoryReq);

    void delete(Integer id);
}
