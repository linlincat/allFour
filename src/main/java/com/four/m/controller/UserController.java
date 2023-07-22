package com.four.m.controller;

import com.four.m.common.ApiRestResponse;
import com.four.m.exception.FourException;
import com.four.m.exception.FourExceptionEnum;
import com.four.m.service.UserService;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("userName") String userName,
                                            @RequestParam("password") String password) throws FourException {
        // StringUtil 字符串的方法
        if (StringUtil.isEmpty (userName)) {
            return ApiRestResponse.error (FourExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtil.isEmpty (password)) {
            return ApiRestResponse.error (FourExceptionEnum.NEED_PASSWORD);
        }
        // 密码长度校验
        if(password.length () < 8) {
            return ApiRestResponse.error (FourExceptionEnum.PASSWORD_TOO_SHORT);
        }

        userService.register (userName, password);
        return ApiRestResponse.success ();
    }
}
