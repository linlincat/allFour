package com.four.m.utils;

import com.four.m.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * 描述:      MD5工具
 * */
public class MD5Utils {
    // 工具类定义一个static方便其它方法使用
    public static String getMD5Str(String strValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance ("MD5");
        return Base64.encodeBase64String (md5.digest ((strValue+ Constant.SALT).getBytes ()));
    }
}
