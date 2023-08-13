package com.four.m.config;

import com.four.m.filter.AdminFilter;
import com.four.m.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/*
 * 描述:      User过滤器的配置
 * */
@Configuration
public class UserFilterConfig {

    @Bean  //让spring识别到
    public UserFilter userFilter() {
        return new UserFilter();
    }

    @Bean(name = "userFilterConf")
    public FilterRegistrationBean userFilterConfig() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(userFilter());
        filterRegistrationBean.addUrlPatterns("/cart/*");
        filterRegistrationBean.addUrlPatterns("/order/*");
        filterRegistrationBean.setName("userFilterConf");
        return filterRegistrationBean;
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
