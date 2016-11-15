package com.zhiyin.manager.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
//@PropertySource("classpath:/config/http-service-url.properties")
public class HttpUrlConfig {

    @Value("${file.zip.content}")
    private String zipContent;

    @Value("${zip.addrlevel.content}")
    private String zipAddrLevelContent;


    @Value("${contents.search.addOrUpdate}")
    private String contentIndexUpdate;


    @Value("${contents.search.deleteById}")
    private String contentIndexDel;

    @Value("${contents.search.deleteAll}")
    private String contentDelAll;




}