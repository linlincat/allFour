package com.four.m.common;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

/*
* 描述:      常量值
* */
@Component
// 为了setFileUploadDir能用value拿到变量添加component注解  下载上传文件
public class Constant {
    public static final String FOUR_USER = "four_user";
    public static final String SALT = "okajo34jo123j4|lkqao{";

//    @Value ("${file.upload.dir}")
    // static态的变量上传的时候获取不到
    public static String FILE_UPLOAD_DIR;

    // 用 setFileUploadDir 方法拿变量赋值到 FILE_UPLOAD_DIR
    @Value ("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    public interface  ProductListOrDerBy {
        Set<String> PRICE_ASC_DESC = Sets.newHashSet ("price desc", "price asc");
    }

    public  interface  SaleStatus {
        int NOT_SALE = 0;  // 商品下架状态
        int SALE  = 1;  // 商品上架状态
    }
    public  interface  Cart {
        int UN_SELECTED = 0;  // 购物车未选中状态
        int SELECTED  = 1;  // 购物车选中状态
    }
}
