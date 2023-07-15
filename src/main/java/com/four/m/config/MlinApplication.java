package com.four.m.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;

//把应用移动到包内需要添加注解
@ComponentScan("com.four")
// 告诉应用我持久化了
@MapperScan("com.four.m.mapper")
@SpringBootApplication
public class MlinApplication {
    private static  final  Logger LOG = LoggerFactory.getLogger(MlinApplication.class);

    public static void main(String[] args) {
//        SpringApplication.run(MlinApplication.class, args);
        SpringApplication app = new SpringApplication(MlinApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }

}
