package com.zhiyin.manager.module.content.controller;

import java.util.List;

import com.zhiyin.manager.module.content.module.BasicContentInfoAddC2s;
import com.zhiyin.manager.module.content.module.SleContentByGroupS2c;
import com.zhiyin.manager.module.content.vo.BasicContentVo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.zhiyin.dbs.module.content.entity.BasicContent;
import com.zhiyin.dbs.module.content.service.IBasicContentService;
import com.zhiyin.manager.module.common.controller.BaseController;
import com.zhiyin.manager.module.common.module.S2cObj;
import com.zhiyin.manager.module.content.vo.BasicContentInfoVo;
import com.zhiyin.manager.module.content.module.SelBasicContentC2s;
import com.zhiyin.manager.web.entity.WebResp;
import com.zhiyin.utils.bean.BeanMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author momo
 * 基本内容controller
 */
@Slf4j
@RestController
@RequestMapping("/contents/")
@Api(value = "基本内容", description = "基本内容的CURD操作")
public class BasicContentController extends BaseController {

    @com.alibaba.dubbo.config.annotation.Reference
    private IBasicContentService basicContentService;

    @ApiOperation(value = "根据id获取基本内容", nickname = "", response = BasicContentInfoVo.class)
    @RequestMapping( value = "/infos/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> getById(@PathVariable Long id) {

        if (id == null || id <= 0) {
            log.info("req info not valid.");
            return failReqRet();
        }
        BasicContent basicContent = basicContentService.selectById(id);
        BasicContentInfoVo s2c = null;
        if (basicContent != null)
            s2c = BeanMapper.map(basicContent, BasicContentInfoVo.class);
        return succRet(s2c);

    }

    @ApiOperation(value = "根据组id获取内容", nickname = "", response = SleContentByGroupS2c.class)
    @RequestMapping(value = "/{groupId}/infos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> getByGid(@PathVariable Long groupId ) {
        List<BasicContent> list = basicContentService.selectByArticleId(groupId);

        List<BasicContentInfoVo> convList = BeanMapper.mapList(list, BasicContentInfoVo.class);
        SleContentByGroupS2c s2c = new SleContentByGroupS2c(convList);
        return succRet( s2c );
    }

    @ApiOperation(value = "增加基本内容", nickname = "", response = WebResp.class)
    @RequestMapping(value = "/infos", method = RequestMethod.PUT, produces =  MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> add(@RequestBody BasicContentInfoAddC2s c2s) {

        BasicContent basicContent = BeanMapper.map(c2s, BasicContent.class);
        Long id = basicContentService.insertSelectiveGet(basicContent);
        if (id > 0){
            return succAddRet(id);
        }
        return failRet("内容添加失败");
    }

    @ApiOperation(value = "更新基本内容", nickname = "", response = WebResp.class)
    @RequestMapping(value = "/infos/{id}", method = RequestMethod.POST, produces =  MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> updateBasicContent(@PathVariable Long id, @RequestBody BasicContentInfoVo c2s) {
        
        BasicContent basicContent = BeanMapper.map(c2s, BasicContent.class);
        int flag = basicContentService.updateByPrimaryKeySelective(basicContent);
        if (flag > 0){
            return succRet(null);
        }
        return failRet("内容更新失败");
    }

    @ApiOperation(value = "删除基本内容", nickname = "", response = WebResp.class)
    @RequestMapping(value = "infos/{id}", method = RequestMethod.DELETE, produces =  MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> delBasicContent(@PathVariable Long id) {

        if (id == null || id <= 0) {
            log.info("req info not valid.");
            return failReqRet();
        }
        int flag = basicContentService.deleteByPrimaryKey(id);
        if (flag > 0){
            return succRet(null);
        }
        return failRet("内容删除失败");
    }
}
