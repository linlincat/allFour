package com.four.m.config;

import com.four.m.filter.AdminFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/*
 * 描述:      Admin过滤器的配置
 * */
@Configuration
public class AdminFilterConfig {

    @Bean  //让spring识别到
    public AdminFilter adminFilter() {
        return new AdminFilter ();
    }

    @Bean(name = "adminFilterConf")
    public FilterRegistrationBean adminFilterConfig() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<> ();
        filterFilterRegistrationBean.setFilter (adminFilter ());
        filterFilterRegistrationBean.addUrlPatterns ("/admin/category/*");
        filterFilterRegistrationBean.addUrlPatterns ("/admin/product/*");
        filterFilterRegistrationBean.addUrlPatterns ("/admin/order/*");
        filterFilterRegistrationBean.setName ("adminFilterConf");
        return filterFilterRegistrationBean;
    }
}


//    @Bean
//    public FilterRegistrationBean<Test1Filter> RegistTest1(){
//        //通过FilterRegistrationBean实例设置优先级可以生效
//        //通过@WebFilter无效
//        FilterRegistrationBean<Test1Filter> bean = new FilterRegistrationBean<Test1Filter>();
//        bean.setFilter(new Test1Filter());//注册自定义过滤器
//        bean.setName("flilter1");//过滤器名称
//        bean.addUrlPatterns("/*");//过滤所有路径
//        bean.setOrder(1);//优先级，最顶级
//        return bean;
//    }
//    @Bean
//    public FilterRegistrationBean<Test2Filter> RegistTest2(){
//        //通过FilterRegistrationBean实例设置优先级可以生效
//        //通过@WebFilter无效
//        FilterRegistrationBean<Test2Filter> bean = new FilterRegistrationBean<Test2Filter>();
//        bean.setFilter(new Test2Filter());//注册自定义过滤器
//        bean.setName("flilter2");//过滤器名称
//        bean.addUrlPatterns("/test/*");//过滤所有路径
//        bean.setOrder(6);//优先级，越低越优先
//        return bean;
//    }
