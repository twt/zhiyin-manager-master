package com.zhiyin.manager.module.test;

import com.zhiyin.manager.config.HttpUrlConfig;
import com.zhiyin.manager.module.common.module.S2cObj;
import com.zhiyin.manager.web.entity.WebResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hg on 2016/6/9.
 */
@Slf4j
@RestController
@RequestMapping("/test/config")
public class TestPropertiesConfig {

    @Autowired
    private HttpUrlConfig httpUrlConfig;

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getByAddressId() {
        return httpUrlConfig.getZipContent();
    }
}