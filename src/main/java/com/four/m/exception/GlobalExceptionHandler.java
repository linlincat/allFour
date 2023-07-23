package com.four.m.exception;

import com.four.m.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

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
    // 因为返回的是一个ApiRestResponse 所以是一个ResponseBody注解  可以设置返回Object改成ApiRestResponse
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiRestResponse MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error ("MethodArgumentNotValidException: ", e);
        return  handleBindingResult(e.getBindingResult ());
    }

    private ApiRestResponse handleBindingResult(BindingResult result) {
        // 对异常处理为对外暴露对提示信息
        List<String> list = new ArrayList<> ();  // 定义为null后面是不能（add）直接添加的
        if (result.hasErrors ()) {
            List<ObjectError> allErrors = result.getAllErrors ();
            for(ObjectError objectError: allErrors) {
                String message = objectError.getDefaultMessage ();
                list.add (message);
            }
        }
        // list==null 上方已经定义ArrayList了，所以不可能在为null了
        if (list.size () == 0) {
            return ApiRestResponse.error (FourExceptionEnum.REQUEST_PARAM);
        }
        return ApiRestResponse.error (FourExceptionEnum.REQUEST_PARAM.getCode (), list.toString ());
    }
}
