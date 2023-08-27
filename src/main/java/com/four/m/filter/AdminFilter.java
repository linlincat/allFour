package com.four.m.filter;

import com.four.m.common.Constant;
import com.four.m.domain.User;
import com.four.m.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * 描述:       管理员校验过滤器
 * 实现方式通过对URL请求地址进行过滤
 * 收到请求后添加对应对方法进行判断进行过滤
 * */
//implements Filter 要用javax.servlet的引用
public class AdminFilter implements Filter {
    @Autowired
    UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init (filterConfig);
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Constant.FOUR_USER);
        if (currentUser == null) {
            PrintWriter out = new HttpServletResponseWrapper(
                    (HttpServletResponse) servletResponse).getWriter();
            out.write("{\n"
                    + "    \"status\": 10007,\n"
                    + "    \"msg\": \"NEED_LOGIN\",\n"
                    + "    \"data\": null\n"
                    + "}");
            out.flush();
            out.close();
            return;
        }
        //校验是否是管理员
        boolean adminRole = userService.checkAdminRole(currentUser);
        if (adminRole) {
            // 往下执行
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            PrintWriter out = new HttpServletResponseWrapper(
                    (HttpServletResponse) servletResponse).getWriter();
            out.write("{\n"
                    + "    \"status\": 10009,\n"
                    + "    \"msg\": \"NEED_ADMIN\",\n"
                    + "    \"data\": null\n"
                    + "}");
            out.flush();
            out.close();
        }
    }


    @Override
    public void destroy() {
//        Filter.super.destroy ();
    }
}
