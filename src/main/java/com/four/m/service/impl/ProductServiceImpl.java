package com.four.m.service.impl;

import com.four.m.common.Constant;
import com.four.m.domain.Product;
import com.four.m.exception.FourException;
import com.four.m.exception.FourExceptionEnum;
import com.four.m.mapper.ProductMapper;
import com.four.m.model.query.ProductListQuery;
import com.four.m.model.request.AddProductReq;
import com.four.m.model.request.ProductListReq;
import com.four.m.model.vo.CategoryVo;
import com.four.m.service.CategoryService;
import com.four.m.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：     商品服务实现类
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryService categoryService;

    @Override
    public void add(AddProductReq addProductReq) {
        Product product = new Product ();
        BeanUtils.copyProperties (addProductReq, product);
        Product productOld = productMapper.selectByName (addProductReq.getName ());
        if (productOld != null) {
            throw new FourException (FourExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.insertSelective (product);
        if (count == 0) {
            throw new FourException (FourExceptionEnum.CREATE_FAIL);
        }
    }

    @Override
    public void update(Product updateProduct) {
        Product productOld = productMapper.selectByName (updateProduct.getName ());
        // 同名且不同ID，不能修改
        if (productOld != null && !productOld.getId ().equals (updateProduct.getId ())) {
            throw new FourException (FourExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.updateByPrimaryKeySelective (updateProduct);
        if (count == 0) {
            throw new FourException (FourExceptionEnum.UPDATE_FAILED);
        }
    }


    @Override
    public void delete(Integer id) {
        Product productOld = productMapper.selectByPrimaryKey (id);
        // 查不到该条记录，无法删除
        if (productOld == null) {
            throw new FourException (FourExceptionEnum.DELETE_FAILED);
        }
        int count = productMapper.deleteByPrimaryKey (id);
        if (count == 0) {
            throw new FourException (FourExceptionEnum.DELETE_FAILED);
        }
    }

    // 通过提示报错，修改直接在接口类中生成类，就没有用@Override注解方式实现
    public void batchUpdateSellStatus(Integer[] ids,
                                      Integer sellStatus) {
        productMapper.batchUpdateSellStatus (ids, sellStatus);
    }


    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage (pageNum, pageSize);
        List<Product> products = productMapper.selectListForAdmin ();
        PageInfo pageInfo = new PageInfo (products);
        return pageInfo;
    }

    @Override
    public Product detail(Integer id) {
        Product product = productMapper.selectByPrimaryKey (id);
        return product;
    }


@Override
    public PageInfo list(ProductListReq productListReq) {
        // 构建query对象
        ProductListQuery productListQuery = new ProductListQuery ();

//        EbookExample ebookExample = new EbookExample();
////        ebookExample.createCriteria(); 相当于where
//        EbookExample.Criteria criteria = ebookExample.createCriteria();
////        andNameLike需要自己添加左右匹配
//        if (!ObjectUtils.isEmpty(req.getName())) {
//            criteria.andNameLike("%" + req.getName() + "%");
//        }
//        if (!ObjectUtils.isEmpty(req.getCategoryId2())) {
//            criteria.andCategory2IdEqualTo(req.getCategoryId2());
//        }

        // 搜索处理
        if (!StringUtil.isEmpty (productListReq.getKeyword ())) {
            // 合成字符串
            String keyword =  new StringBuffer ().append ("%").append (productListReq.getKeyword ()).append ("%").toString ();
            productListQuery.setKeyword (keyword);

            //目录处理： 不仅是需要查当前目录，还要吧所有子目录的所有商品都查出来
            if(productListReq.getCategoryId() != null) {
                List<CategoryVo> categoryVoList = categoryService.listCategoryForCustomer (productListReq.getCategoryId ());
                ArrayList<Integer> categoryIds = new ArrayList<> ();
                categoryIds.add(productListReq.getCategoryId ());
                getCategoryIds (categoryVoList, categoryIds);
                productListQuery.setCategoryIds (categoryIds);

            }

            // 排序处理
            String orderBy = productListReq.getOrderBy ();  // 前端传递过来
            if(Constant.ProductListOrDerBy.PRICE_ASC_DESC.contains (orderBy)) {
                PageHelper.startPage (productListReq.getPageNum (), productListReq.getPageSize (), orderBy);
            }else {
                PageHelper.startPage (productListReq.getPageNum (), productListReq.getPageSize ());
            }

        }

        List<Product> productList = productMapper.selectList (productListQuery);
        PageInfo pageInfo = new PageInfo (productList);
        return  pageInfo;
    }

    private void getCategoryIds(List<CategoryVo> categoryVoList, ArrayList<Integer> categoryIds) {

        for (int i = 0; i <categoryVoList.size (); i++) {
            CategoryVo categoryVo = categoryVoList.get (i);
            if (categoryVo!=null) {
                categoryIds.add (categoryVo.getId ());
                getCategoryIds (categoryVo.getChildCategroy (), categoryIds);
            }
        }
    }
}
