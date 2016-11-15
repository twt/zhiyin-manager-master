package com.zhiyin.manager.module.anchor.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;


import com.zhiyin.dbs.module.anchor.entity.AnchorRoleInfo;
import com.zhiyin.dbs.module.anchor.service.IAnchorRoleInfoService;
import com.zhiyin.file.qiniu.FileBucket;
import com.zhiyin.file.qiniu.FileBucketConvert;
import com.zhiyin.manager.module.address.module.CustomAddressS2c;
import com.zhiyin.manager.module.anchor.module.AnchorRoleInfoVo;
import com.zhiyin.manager.module.anchor.module.GetAnchorRoleInfoS2c;
import com.zhiyin.manager.module.common.controller.BaseController;
import com.zhiyin.manager.module.common.module.AddSuccS2c;
import com.zhiyin.manager.module.common.module.S2cObj;
import com.zhiyin.manager.web.entity.WebResp;
import com.zhiyin.utils.bean.BeanMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 主播角色
 * 
 * @author hg
 * 
 */
@Slf4j
@RestController
@RequestMapping("/anchors/")
@Api(value = "主播角色操作", description = "")
public class AnchorRoleInfoController extends BaseController {

	@com.alibaba.dubbo.config.annotation.Reference
	private IAnchorRoleInfoService anchorRoleInfoService;


    @ApiOperation(value = "查询角色", nickname = "", response = AnchorRoleInfoVo.class)
    @RequestMapping(value = "roles/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> get( @PathVariable("id") Long id) {
        AnchorRoleInfo sel = anchorRoleInfoService.selectById(id);
        if(sel == null){
            log.error("sel role not found, id:{}",id);
            return notFound();
        }
        log.info(JSON.toJSONString(sel));
        AnchorRoleInfoVo s2c = conv(sel);
        log.info(JSON.toJSONString(s2c));
        return succRet(s2c);
    }


    @ApiOperation(value = "修改角色", nickname = "", response = S2cObj.class)
    @RequestMapping(value = "roles/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> update(@PathVariable("id") Long id,  @RequestBody AnchorRoleInfoVo vo) {

//        long id = vo.getId();
        AnchorRoleInfo sel = anchorRoleInfoService.selectById(id);
        if(sel == null){
            log.error("sel role not found, id:{}",id);
            return notFound();
        }
        int ret = anchorRoleInfoService.updateByPrimaryKeySelective(conv(vo));
        if(ret == 1){
            return succRet(null);
        }else{
            log.error("update info error.");
            return failReqRet();
        }
    }

    @ApiOperation(value = "新增角色", nickname = "", response = AddSuccS2c.class)
    @RequestMapping(value = "roles", method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> add(  @RequestBody AnchorRoleInfoVo vo) {

        long ret = anchorRoleInfoService.insertSelectiveGet(conv(vo));
        if(ret > 0){
            return succAddRet(ret);
        }else{
            log.error("insert info error.");
            return failReqRet();
        }
    }


    @ApiOperation(value = "删除角色", nickname = "", response = WebResp.class)
    @RequestMapping(value = "roles/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> delete( @PathVariable("id") Long id ) {

        long ret = anchorRoleInfoService.deleteByPrimaryKey(id);
        if(ret > 0){
            return succRet(null);
        }else{
            log.error("insert info error.");
            return failReqRet();
        }

    }


    @ApiOperation(value = "获取所有角色", nickname = "", response = AnchorRoleInfoVo.class)
    @RequestMapping(value = "roles", method = RequestMethod.GET )
	public WebResp<S2cObj> list( ) {

		PageInfo<AnchorRoleInfo> pageInfo = anchorRoleInfoService.selectByPage(1, 20);

		List<AnchorRoleInfo> list = pageInfo.getList();
		List<AnchorRoleInfoVo> voList = Lists.newArrayList();
		for (AnchorRoleInfo tmp : list) {
			voList.add( conv(tmp) );
		}

		GetAnchorRoleInfoS2c s2c = new GetAnchorRoleInfoS2c();
		s2c.setList( voList);
//		s2c.setCurrentPage(  pageInfo.getPageNum());
//		s2c.setPageSize( pageInfo.getPageSize());
//		s2c.setTotalPages( pageInfo.getPages());
		return succRet(s2c);
	}


    public static AnchorRoleInfoVo conv(AnchorRoleInfo tmp){
        AnchorRoleInfoVo vo = BeanMapper.map(tmp,AnchorRoleInfoVo.class);
        vo.setAudio( FileBucketConvert.getUrl(FileBucket.RoleAudioIntro) + tmp.getAudio() );
        vo.setAvatar( FileBucketConvert.getUrl(FileBucket.RoleAvatar) + tmp.getAvatar() );
        return vo;
    }

    public static AnchorRoleInfo conv( AnchorRoleInfoVo tmp){
        AnchorRoleInfo entity = BeanMapper.map(tmp , AnchorRoleInfo.class);
//        vo.setAudio( FileBucketConvert.getUrl(FileBucket.RoleAudioIntro) + tmp.getAudio() );
//        vo.setAvatar( FileBucketConvert.getUrl(FileBucket.RoleAvatar) + tmp.getAvatar() );
        return entity;
    }
}
