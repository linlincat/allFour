package com.four.m.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 告诉spring boot这里是一个注解
@Configuration
/*
*1 addInterceptors：拦截器
*2 addViewControllers：页面跳转
*3 addResourceHandlers：静态资源
*4 configureDefaultServletHandling：默认静态资源处理器
*5 configureViewResolvers：视图解析器
*6 configureContentNegotiation：配置内容裁决的一些参数
*7 addCorsMappings：跨域
*8 configureMessageConverters：信息转换器
* */
public class CorsConfig implements WebMvcConfigurer {
// spring boot 会自动加载进行跨域的处理
    @Override
    public void  addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedHeaders(CorsConfiguration.ALL)
                .allowedMethods(CorsConfiguration.ALL)
                .allowCredentials(true)
                .maxAge(3600); // 1小时内不需要再预检（发OPTIONS请求）
    }
}


//@Configuration
//
//public class CorsConfig implements WebMvcConfigurer {
//
//    @Override public void addCorsMappings(CorsRegistry registry) { registry.addMapping("/**") .allowedOrigins("*")
//    .allowCredentials(true) .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") .maxAge(3600); }
//
//}
