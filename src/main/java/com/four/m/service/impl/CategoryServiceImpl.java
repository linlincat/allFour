package com.four.m.service.impl;


import com.four.m.domain.Category;
import com.four.m.exception.FourException;
import com.four.m.exception.FourExceptionEnum;
import com.four.m.mapper.CategoryMapper;
import com.four.m.model.request.AddCategoryReq;
import com.four.m.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * 描述:       目录分类 Service实现类
 * */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public void add(AddCategoryReq addCategoryReq) {
        Category category = new Category ();

        BeanUtils.copyProperties (addCategoryReq, category);
        Category categoryOld = categoryMapper.selectByName (addCategoryReq.getName ());
        if (categoryOld != null) {
            throw new FourException (FourExceptionEnum.NAME_EXISTED);
        }
        int count = categoryMapper.insertSelective (category);
        if (count == 0) {
            throw new FourException (FourExceptionEnum.CREATE_FAIL);
        }

    }
}
