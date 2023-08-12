package com.four.m.filter;

import com.four.m.common.Constant;
import com.four.m.domain.User;
import com.four.m.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * 描述:       用户过滤器
 * 实现方式通过对URL请求地址进行过滤
 * 收到请求后添加对应对方法进行判断进行过滤
 * */
//implements Filter 要用javax.servlet的引用
public class UserFilter implements Filter {

    public static  User currentUser;
    @Autowired
    UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        HttpSession session = request1.getSession ();
         currentUser = (User) session.getAttribute (Constant.FOUR_USER);
        if (currentUser == null) {
            PrintWriter out = new HttpServletResponseWrapper (
                    (HttpServletResponse) response).getWriter ();
            out.write ("{\n"
                    + "    \"status\": 10007,\n"
                    + "    \"msg\": \"NEED_LOGIN\",\n"
                    + "    \"data\": null\n"
                    + "}");
//            flush是把流里的缓冲数据输出，flush以后还能继续使用这个OutputStream。
//            close是把这个流关闭了，也就是说以后这个OutputStream就不能用了，不过关闭之前会把缓冲的数据都输出
            out.flush ();
            out.close ();
            return;
//            return ApiRestResponse.error (FourExceptionEnum.NEED_LOGIN);
        }
        chain.doFilter (request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init (filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy ();
    }
}
