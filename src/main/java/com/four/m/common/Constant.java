package com.four.m.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*
* 描述:      常量值
* */
@Component
// 为了setFileUploadDir能用value拿到变量添加component注解
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
}
