package com.four.m.controller;

import com.four.m.common.ApiRestResponse;
import com.four.m.common.Constant;
import com.four.m.domain.User;
import com.four.m.exception.FourExceptionEnum;
import com.four.m.model.request.AddCategoryReq;
import com.four.m.service.CategoryService;
import com.four.m.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/*
 * 描述:    商品目录-分类
 * */
@RestController
public class CategoryController {
    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @ApiOperation ("添加目录/分类")
    @PostMapping("admin/category/add")
    @ResponseBody
    public ApiRestResponse addCategory(HttpSession session, @Valid @RequestBody AddCategoryReq addCategoryReq) {
        User currentUser = (User) session.getAttribute (Constant.FOUR_USER);
        if (currentUser == null) {
            return ApiRestResponse.error (FourExceptionEnum.NEED_LOGIN);
        }
        boolean adminRole = userService.checkAdminRole (currentUser);
        if (adminRole) {
            categoryService.add (addCategoryReq);
            return ApiRestResponse.success ();
        } else {
            return ApiRestResponse.error (FourExceptionEnum.NEED_ADMIN);
        }
    }
}
