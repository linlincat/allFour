package com.four.m.controller;

import com.four.m.common.ApiRestResponse;
import com.four.m.common.Constant;
import com.four.m.domain.User;
import com.four.m.exception.FourException;
import com.four.m.exception.FourExceptionEnum;
import com.four.m.service.UserService;
import com.github.pagehelper.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    @Resource
    UserService userService;

    @ApiOperation("用户注册")
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
        if (password.length () < 8) {
            return ApiRestResponse.error (FourExceptionEnum.PASSWORD_TOO_SHORT);
        }

        userService.register (userName, password);
        return ApiRestResponse.success ();
    }
    @ApiOperation ("用户登录")
    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(@RequestParam("userName") String userName,
                                 @RequestParam("password") String password,
                                 HttpSession session) throws FourException {
        // StringUtil 字符串的方法
        if (StringUtil.isEmpty (userName)) {
            return ApiRestResponse.error (FourExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtil.isEmpty (password)) {
            return ApiRestResponse.error (FourExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login (userName, password);
        // 防止密码泄露返回给用户的时候清空
        user.setPassword (null);
        // session 缓存
        session.setAttribute (Constant.FOUR_USER, user);
        return ApiRestResponse.success (user);
    }
    @ApiOperation ("更新签名")
    @PostMapping("/user/update")
    @ResponseBody
    public ApiRestResponse updateUserInfo(HttpSession session, @RequestParam String signature) throws FourException {
        User currentUser = (User) session.getAttribute (Constant.FOUR_USER);
        if (currentUser == null) {
            return ApiRestResponse.error (FourExceptionEnum.NEED_LOGIN);
        }
        User user = new User ();
        user.setId (currentUser.getId ());
        user.setPersonalizedSignature (signature);
        userService.updateInformation (user);
        return ApiRestResponse.success ();
    }

    @ApiOperation ("退出用户")
    @PostMapping("/user/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session) {
        session.removeAttribute (Constant.FOUR_USER);
        return ApiRestResponse.success ();
    }

    @ApiOperation ("管理员身份登录")
    @PostMapping("/adminLogin")
    @ResponseBody
    public ApiRestResponse adminLogin(@RequestParam("userName") String userName,
                                      @RequestParam("password") String password,
                                      HttpSession session) throws FourException {
        // StringUtil 字符串的方法
        if (StringUtil.isEmpty (userName)) {
            return ApiRestResponse.error (FourExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtil.isEmpty (password)) {
            return ApiRestResponse.error (FourExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login (userName, password);
        // 校验是否为管理员
        if (userService.checkAdminRole (user)) {
            // 防止密码泄露返回给用户的时候清空
            user.setPassword (null);
            // session 缓存
            session.setAttribute (Constant.FOUR_USER, user);
            return ApiRestResponse.success (user);
        } else {
            return ApiRestResponse.error (FourExceptionEnum.NEED_ADMIN);
        }
    }
}
