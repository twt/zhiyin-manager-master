package com.zhiyin.manager.module.content.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhiyin.dbs.module.content.service.IContentSubjectListenService;
import com.zhiyin.manager.module.common.controller.BaseController;

import lombok.extern.slf4j.Slf4j;

/** 
 * @author momo
 */
@Slf4j
@RestController
@RequestMapping("content/subject")
public class ContentSubjectListenController extends BaseController {
    @com.alibaba.dubbo.config.annotation.Reference
    private IContentSubjectListenService contentSubjectListenService;

}
