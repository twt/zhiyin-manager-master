package com.zhiyin.manager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:/applicationContext-dubbo.xml")
public class DubboConfig {

}
