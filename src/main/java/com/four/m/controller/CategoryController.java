package com.four.m.controller;

import com.four.m.common.ApiRestResponse;
import com.four.m.common.Constant;
import com.four.m.domain.User;
import com.four.m.exception.FourExceptionEnum;
import com.four.m.model.request.AddCategoryReq;
import com.four.m.service.CategoryService;
import com.four.m.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/*
 * 描述:    商品目录-分类
 * */
@RestController
public class CategoryController {
    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @PostMapping("admin/category/add")
    @ResponseBody
    public ApiRestResponse addCategory(HttpSession session, @RequestBody AddCategoryReq addCategoryReq) {
        if (addCategoryReq.getName () == null
                || addCategoryReq.getType () == null
                || addCategoryReq.getOrderNum () == null
                || addCategoryReq.getParentId () == null) {
            return ApiRestResponse.error (FourExceptionEnum.PARA_NOT_NULL);
        }
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
