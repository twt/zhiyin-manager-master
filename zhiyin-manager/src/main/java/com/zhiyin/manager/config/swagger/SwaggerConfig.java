package com.zhiyin.manager.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by wangqinghui on 2016/2/4.
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket AddressApi() {
        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).groupName("热点接口")
                .apiInfo(apiInfo()).select().paths(regex("/addr.*")).build();
    }

    @Bean
    public Docket ContentApi() {
        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).groupName("内容接口")
                .apiInfo(apiInfo()).select().paths(regex("/content.*")).build();
    }

    @Bean
    public Docket UserApi() {
        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).groupName("用户接口")
                .apiInfo(apiInfo()).select().paths(regex("/user.*")).build();
    }

    @Bean
    public Docket AnchorApi() {
        ApiInfo apiInfo = new ApiInfoBuilder().title("主播接口文档").build();

        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).groupName("主播角色")
                .apiInfo(apiInfo()).select().paths(regex("/anchor.*")).build();
    }

    @Bean
    public Docket FileApi() {
        ApiInfo apiInfo = new ApiInfoBuilder().title("文件接口文档").build();

        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).groupName("文件接口")
                .apiInfo(apiInfo()).select().paths(regex("/files.*")).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("知音文档")
                .contact(new Contact("huiwq1990","","huiwq1990@163.com"))
                .description("Restful Api")
//                .termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
//                .contact("Niklas Heidloff").license("Apache License Version 2.0")
                .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE").version("2.0")
                .build();
    }

}
