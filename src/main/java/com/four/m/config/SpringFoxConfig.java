package com.four.m.config;

import io.swagger.annotations.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

//@Api()
//用于类，标识这个类是swagger的资源 ，主要用在controller类上，会在接口文档上显示当前类说明
//@ApiOperation()
//用于方法，在接口文档上面对接口进行说明，是swagger最主要的注解
//@ApiParam()
//用于方法，参数，字段说明，在方法上面对参数进行说明，会在接口文档上面显示参数的说明
//@ApiModel()
//用于类，表示对类进行说明，用于参数用实体类接收时，在接口文档上面会显示这个类里所有字段的说明
//@ApiIgnore()
//用于类，方法，方法参数，表示这个方法或者类被忽略，即不会显示在接口文档里面
//
//@ApiImplicitParam()
//用于方法，表示单独的请求参数，多数时候可以用@ApiParm替代
//@ApiImplicitParams()
//用于方法，包含多个 @ApiImplicitParam

// 配置注解
@Configuration
public class SpringFoxConfig {

    //访问http://localhost:8083/swagger-ui.html可以看到API文档
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("今天星期四")
                .description("在一个星期四的早上")
                .termsOfServiceUrl("")
                .build();
    }
}