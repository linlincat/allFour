package com.four.m.exception;

import com.four.m.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/*
* 描述：    处理统一异常
* */

// 拦截异常的注解
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger (GlobalExceptionHandler.class);
    // 系统错误
    // 统一处理方法抛出的异常
    @ExceptionHandler(Exception.class)
    // 因为返回的是一个ApiRestResponse
    @ResponseBody
    public Object handleExcetion(Exception e) {
        log.error ("Default Excepion: ", e);
        return ApiRestResponse.error (FourExceptionEnum.SYSTEM_ERROR);
    }
    // 统一异常
    @ExceptionHandler(FourException.class)
    // 因为返回的是一个ApiRestResponse
    @ResponseBody
    public Object handleFourExcetion(FourException e) {
        log.error ("Four Excepion: ", e);
        return ApiRestResponse.error (e.getCode (), e.getMessage ());
    }
}
