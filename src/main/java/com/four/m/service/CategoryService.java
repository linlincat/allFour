package com.four.m.service;

/*
* 描述:      分类目录 Service
* */

import com.four.m.model.request.AddCategoryReq;
import com.four.m.model.vo.CategoryVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryService {
    void add(AddCategoryReq addCategoryReq);

    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    // ArrayList需要范型的时候加上<>
    List<CategoryVo> listCategoryForCustomer(Integer parentId);
}
