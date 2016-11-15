package com.zhiyin.manager.module.content.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhiyin.dbs.module.content.service.IContentSubjectListenCacheService;
import com.zhiyin.manager.module.common.controller.BaseController;

import lombok.extern.slf4j.Slf4j;

/** 
 * @author momo
 */
@Slf4j
@RestController
@RequestMapping("content/subject/cache")
public class ContentSubjectListenCacheController extends BaseController {

    @com.alibaba.dubbo.config.annotation.Reference
    private IContentSubjectListenCacheService contentSubjectListenCacheService;

}
