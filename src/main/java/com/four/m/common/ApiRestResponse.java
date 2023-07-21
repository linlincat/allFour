package com.four.m.common;

import com.four.m.exception.FourExceptionEnum;

/*
 * 描述：   通用返回对象
 * */
public class ApiRestResponse<T> {

    private Integer status;

    private String msg;

    private T data;

    // 定义好常量
    private static final int OK_CODE = 10000;
    private static final String OK_MSG = "SUCCESS";

    public ApiRestResponse(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ApiRestResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ApiRestResponse() {
        this (OK_CODE, OK_MSG);
    }

    // 返回一个ApiRestResponse<T>
    // 调用success后会创建一个ApiRestResponse 没有参数执行无参数构造函数
    public static <T> ApiRestResponse<T> success() {
        return new ApiRestResponse<> ();
    }

    public static <T> ApiRestResponse<T> success(T result) {
        ApiRestResponse<T> response = new ApiRestResponse<> ();
        response.setData (result);
        return response;
    }

    public static <T> ApiRestResponse<T> error(Integer code, String msg) {
        return new ApiRestResponse<> (code, msg);
    }

    public static <T> ApiRestResponse<T> error(FourExceptionEnum ex) {
        return new ApiRestResponse<> (ex.getCode (), ex.getMsg ());
    }

    @Override
    public String toString() {
        return "ApiRestResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
