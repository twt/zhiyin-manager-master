package com.zhiyin.manager.module.content.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.zhiyin.app.api.content.module.ZipAddContentS2c;
import com.zhiyin.dbs.module.content.entity.BasicContent;
import com.zhiyin.dbs.module.content.entity.ContentGroup;
import com.zhiyin.dbs.module.content.service.IBasicContentService;
import com.zhiyin.dbs.module.content.service.IContentGroupService;
import com.zhiyin.http.factory.HttpRequestFactory;
import com.zhiyin.manager.config.HttpUrlConfig;
import com.zhiyin.manager.module.common.controller.BaseController;
import com.zhiyin.manager.module.common.module.S2cObj;
import com.zhiyin.manager.module.content.module.DangerousOpCodeC2s;
import com.zhiyin.manager.module.content.module.GenContentZipByAddrLevelC2s;
import com.zhiyin.manager.web.entity.WebResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by hg on 2016/6/10.
 */
@Slf4j
@RestController
@RequestMapping("/contents/index/")
public class ContentIndexController extends BaseController {

    @Autowired
    HttpUrlConfig httpUrlConfig;

    @com.alibaba.dubbo.config.annotation.Reference
    private IContentGroupService contentGroupService;
    @com.alibaba.dubbo.config.annotation.Reference
    private IBasicContentService basicContentService;

    @ApiOperation(value = "清空内容索引", nickname = "", response = WebResp.class)
    @RequestMapping(value = "deleteAll", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> deleteAll(@RequestBody DangerousOpCodeC2s c2s) {
        try {
            Map<String,String> req = Maps.newHashMap();
            req.put("accCode",c2s.getAccCode());
            HttpRequestFactory.post( httpUrlConfig.getContentDelAll(), req);
        } catch (Exception e) {
            log.error("del all content index error.");
            return failRet(e.getMessage());
        }
        return succRet(null);
    }

    @ApiOperation(value = "重建内容索引", nickname = "", response = WebResp.class)
    @RequestMapping(value = "reIndex", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> reIndex(@RequestBody DangerousOpCodeC2s c2s) {

        int page = 1;
        int pageSize = 30;
        int count = 1;
        while(true){
            PageInfo<ContentGroup> pageInfo = contentGroupService.selectByPage(page, pageSize);
            log.info("process page {},",page);
            log.info("page result, cur page:{}, total page:{}.",pageInfo.getPageNum(),pageInfo.getPages());
            List<ContentGroup> cgList = pageInfo.getList();
            for(int i =0; i<cgList.size() ;i++){
                // 内容组包含的内容
                List<BasicContent> bcList = basicContentService.selectByCgroupId(cgList.get(i).getId());
                for(BasicContent bc : bcList){
                    try {
                        HttpRequestFactory.post( httpUrlConfig.getContentIndexUpdate(), bc);
                    } catch (Exception e) {
                        log.error("redo all content index error.");
                        return failRet(e.getMessage());
                    }
                    count++;
                }
            }
            page++;
            if(pageInfo.isIsLastPage() || page > pageInfo.getPages()){
                break;
            }
        }
        log.info("build content index succ. {}",count);
        return succRet(null);
    }

}
