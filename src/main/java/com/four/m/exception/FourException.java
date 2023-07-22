package com.four.m.exception;

/*
* 描述：     统一异常
* */
public class FourException extends Exception {
    private final Integer code;
    private final String message;

    public FourException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public FourException(FourExceptionEnum exceptionEnum) {

        this(exceptionEnum.getCode (), exceptionEnum.getMsg ());
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
