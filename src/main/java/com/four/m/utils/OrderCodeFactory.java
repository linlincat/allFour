package com.four.m.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/*
* 描述:        生成订单No工具类
* */
public class OrderCodeFactory {
    private  static  String getDateTime() {
        DateFormat sdf = new SimpleDateFormat ("yyyyMMddHHmmss");
        return sdf.format (new Date ());
    }

    private  static int getRandom(Long n ) {
        Random random = new Random ();
        return (int)(random.nextDouble () * (9000)) + 10000;
    }

    public  static  String getOrderCode(Long userId) {
        return getDateTime () + getRandom (userId);
    }
}
