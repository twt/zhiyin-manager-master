package com.zhiyin.manager.module.content.controller;

import com.github.pagehelper.PageInfo;
import com.zhiyin.dbs.module.anchor.entity.AnchorRoleInfo;
import com.zhiyin.dbs.module.anchor.service.IAnchorRoleInfoService;
import com.zhiyin.manager.module.address.module.CustomAddressPageS2c;
import com.zhiyin.manager.module.content.module.ListContentGroupC2s;
import com.zhiyin.manager.module.content.module.ListContentGroupS2c;
import com.zhiyin.manager.module.user.vo.ListUserInfoS2c;
import com.zhiyin.manager.module.user.vo.UserInfoVo;
import com.zhiyin.manager.util.PageInfoUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.zhiyin.dbs.module.content.entity.ContentGroup;
import com.zhiyin.dbs.module.content.service.IContentGroupService;
import com.zhiyin.manager.module.common.controller.BaseController;
import com.zhiyin.manager.module.common.module.S2cObj;
import com.zhiyin.manager.module.content.vo.ContentGroupVo;
import com.zhiyin.manager.web.entity.WebResp;
import com.zhiyin.utils.bean.BeanMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 内容组controller
 * @author momo
 */
@Slf4j
@RestController
@RequestMapping("/contents/groups")
@Api(value = "内容组", description = "内容组的CURD操作")
public class ContentGroupController extends BaseController {

    @com.alibaba.dubbo.config.annotation.Reference
    private IAnchorRoleInfoService anchorRoleInfoService;
    @com.alibaba.dubbo.config.annotation.Reference
    private IContentGroupService contentGroupService;

    @ApiOperation(value = "分页查询内容组", nickname = "", response = ListContentGroupS2c.class)
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> list( @RequestBody ListContentGroupC2s vo) {

        PageInfo ret = contentGroupService.selectByPage(vo.getPageNum(), vo.getPageSize());

        List<ContentGroupVo> list = BeanMapper.mapList(ret.getList(), ContentGroupVo.class);

        // 设置角色名称
        for(ContentGroupVo tmp : list){
            AnchorRoleInfo anchor = anchorRoleInfoService.selectById(tmp.getRoleId());
            if(anchor != null){
                tmp.setRoleName( anchor.getName());
            }else{
                log.error("anchor {} not exist,", tmp.getRoleId());
            }
        }

        ListContentGroupS2c s2c = new ListContentGroupS2c();
        BeanMapper.copy(PageInfoUtil.s2c(ret),s2c);
        s2c.setList( list );

        return succRet(s2c);
    }

    @ApiOperation(value = "根据id查找内容组", nickname = "", response = ContentGroupVo.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> getById(@PathVariable Long id) {

        if (id == null || id <= 0) {
            log.error("req info not valid.");
            return failReqRet();
        }

        ContentGroup contentGroup = contentGroupService.selectById(id);
        if(contentGroup == null){
            return notFound();
        }

        ContentGroupVo s2c = BeanMapper.map(contentGroup, ContentGroupVo.class);
        return succRet(s2c);
    }


    @ApiOperation(value = "内容组删除", nickname = "", response = WebResp.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> del(@PathVariable Long id) {
        if (id == null || id <= 0) {
            log.info("req info not valid.");
            return failReqRet();
        }
        int flag = contentGroupService.deleteByPrimaryKey(id);
        if (flag > 0){
            return succRet(null);
        }

        log.error("删除失败，id:{}",id);
        return failRet("内容组删除失败");
    }

    @ApiOperation(value = "内容组添加", nickname = "", response = WebResp.class)
    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> add(@RequestBody ContentGroupVo c2s) {
        ContentGroup contentGroup = BeanMapper.map(c2s, ContentGroup.class);
        long id = contentGroupService.insertSelectiveGet(contentGroup);

        if ( id > 0 ){
            return succAddRet(id);
        }

        return failRet("内容组添加失败");
    }

    @ApiOperation(value = "内容组修改", nickname = "", response = WebResp.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> modifyContentGroup(@PathVariable Long id, @RequestBody ContentGroupVo c2s) {
        ContentGroup contentGroup = BeanMapper.map(c2s, ContentGroup.class);
        contentGroup.setId(id);
        int flag = contentGroupService.updateByPrimaryKeySelective(contentGroup);
        if (flag > 0){
            return succRet(null);
        }
        log.error("修改失败，id:{}",id);
        return failRet("内容组修改失败");
    }

}
