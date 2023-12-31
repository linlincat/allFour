package com.four.m.service.impl;


import com.four.m.common.ApiRestResponse;
import com.four.m.domain.Category;
import com.four.m.exception.FourException;
import com.four.m.exception.FourExceptionEnum;
import com.four.m.mapper.CategoryMapper;
import com.four.m.model.request.AddCategoryReq;
import com.four.m.model.vo.CategoryVo;
import com.four.m.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/*
 * 描述:       目录分类 Service实现类
 * 因为它是实现类所以不能直接返回ApiRestResponse
 * 需要通过 throw 抛出
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

    @Override
    public void delete(Integer id) {
        Category categoryOld = categoryMapper.selectByPrimaryKey (id);
        // 查不到记录， 无法删除 删除失败
        if (categoryOld == null) {
            throw new FourException (FourExceptionEnum.DELETE_FAILED);
        }
        int count = categoryMapper.deleteByPrimaryKey (id);
        if (count == 0) {
            throw new FourException (FourExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        //如果多个字段排序，逗号隔开，注意字段必须是数据库中的字段
        //"approve_state desc , id desc"
//        orderBy和pageNum/pageSize一样,都是Pagehelper通过MyBatis拦截器,在query查询中注入进去的,所以在前端传参时,orderBy参数应为数据库column
//        desc/asc这种形式,多字段排序则可以用逗号(,)拼接,譬如: columnA desc,columnB,
//        PageHelper.startPage (pageNum, pageSize, "type, order_num");
        PageHelper.startPage (pageNum, pageSize, "type desc, order_num desc");
        List<Category> categoryList = categoryMapper.selectList ();
        PageInfo pageInfo = new PageInfo (categoryList);
        return pageInfo;
    }


    @Override
    // ArrayList需要范型的时候加上<>
    public List<CategoryVo> listCategoryForCustomer(Integer parentId) {
        ArrayList<CategoryVo> categoryVoList = new ArrayList<> ();
        // 其实就是【前端的】一个递归方法
        recursivelyFindCategories (categoryVoList, parentId);
        return categoryVoList;
    }

    private void recursivelyFindCategories(
            List<CategoryVo> categoryVoList, Integer parentId
    ) {
        // 递归获取所有的子类别，并组合成为一个目录树
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId (parentId);
        if (!CollectionUtils.isEmpty (categoryList)) {
            for (int i = 0; i < categoryList.size (); i++) {
                Category category = categoryList.get (i);
                CategoryVo categoryVo = new CategoryVo ();
                BeanUtils.copyProperties (category, categoryVo);
                categoryVoList.add (categoryVo);
                // copyProperties过来缺少childCategroy
                recursivelyFindCategories (categoryVo.getChildCategroy (), categoryVo.getId ());
            }
        }
    }
}
