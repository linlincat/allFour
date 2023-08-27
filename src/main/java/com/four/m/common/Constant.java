package com.four.m.common;

import com.four.m.exception.FourException;
import com.four.m.exception.FourExceptionEnum;
import com.google.common.collect.Sets;
import jdk.nashorn.internal.runtime.arrays.IteratorAction;
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
    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    public interface ProductListOrDerBy {
        Set<String> PRICE_ASC_DESC = Sets.newHashSet ("price desc", "price asc");
    }

    public interface SaleStatus {
        int NOT_SALE = 0;  // 商品下架状态
        int SALE = 1;  // 商品上架状态
    }

    public interface Cart {
        int UN_SELECTED = 0;  // 购物车未选中状态
        int SELECTED = 1;  // 购物车选中状态
    }

    public enum OrderStatusEnum {
        CANCELED (0, "用户已取消"),
        NOT_PAID (10, "未付款"),
        PAID (20, "已付款"),
        DELIVERED (30, "已发货"),
        FINISHED (40, "交易完成");

        private String value;
        private int code;

        OrderStatusEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public static OrderStatusEnum codeOf(int code) {
            for (OrderStatusEnum orderStatusEnum : values ()) {
                if (orderStatusEnum.getCode () == code) {
                    return orderStatusEnum;
                }
            }
            throw new FourException (FourExceptionEnum.NO_ENUM);
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
