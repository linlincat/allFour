package com.four.m.service.impl;

import com.four.m.domain.Product;
import com.four.m.exception.FourException;
import com.four.m.exception.FourExceptionEnum;
import com.four.m.mapper.ProductMapper;
import com.four.m.model.request.AddProductReq;
import com.four.m.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * 描述：     商品服务实现类
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

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
        if(productOld !=null && !productOld.getId ().equals (updateProduct.getId ())){
            throw new FourException (FourExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.updateByPrimaryKeySelective (updateProduct);
        if (count==0) {
            throw new FourException (FourExceptionEnum.UPDATE_FAILED);
        }
    }



    @Override
    public void delete(Integer id) {
        Product productOld = productMapper.selectByPrimaryKey (id);
        // 查不到该条记录，无法删除
        if(productOld ==null){
            throw new FourException (FourExceptionEnum.DELETE_FAILED);
        }
        int count = productMapper.deleteByPrimaryKey (id);
        if (count==0) {
            throw new FourException (FourExceptionEnum.DELETE_FAILED);
        }
    }



}
