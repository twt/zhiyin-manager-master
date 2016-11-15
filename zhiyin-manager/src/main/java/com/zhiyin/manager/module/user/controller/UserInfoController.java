package com.zhiyin.manager.module.user.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zhiyin.dbs.module.address.service.ICustomAddressService;
import com.zhiyin.dbs.module.user.entity.UserInfo;
import com.zhiyin.dbs.module.user.service.IUserInfoService;
import com.zhiyin.manager.module.address.module.CustomAddressS2c;
import com.zhiyin.manager.module.anchor.module.AnchorRoleInfoVo;
import com.zhiyin.manager.module.common.controller.BaseController;
import com.zhiyin.manager.module.common.module.AddSuccS2c;
import com.zhiyin.manager.module.common.module.S2cObj;
import com.zhiyin.manager.module.user.vo.ListUserInfoC2s;
import com.zhiyin.manager.module.user.vo.ListUserInfoS2c;
import com.zhiyin.manager.module.user.vo.UserInfoVo;
import com.zhiyin.manager.util.PageInfoUtil;
import com.zhiyin.manager.web.entity.WebResp;
import com.zhiyin.utils.bean.BeanMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * 用户信息接口
 */
@Slf4j
@RestController
@RequestMapping("/users")
@Api(value = "用户基本信息接口", description = "用户信息修改、删除、查询接口，目前没有增加")
public class UserInfoController extends BaseController {

    @com.alibaba.dubbo.config.annotation.Reference
    private IUserInfoService userInfoService ;

//    @ApiOperation(value = "/infos", nickname = "", response = CustomAddressS2c.class)
//    @RequestMapping(method = RequestMethod.GET, path = "/test" )
//    public WebResp<S2cObj> add(@RequestBody AnchorRoleInfoVo vo) {
//
//        long ret = userInfoService.insertSelectiveGet(conv(vo));
//        if(ret > 0){
//            AddSuccS2c s2c = new AddSuccS2c();
//            s2c.setId(ret);
//            return succRet(s2c);
//        }else{
//            log.error("insert info error.");
//            return failReqRet();
//        }
//    }

    @ApiOperation(value = "获取所有用户", nickname = "", response = ListUserInfoS2c.class)
    @RequestMapping(method = RequestMethod.POST, path = "/infos" )
    public WebResp<S2cObj> list(@RequestBody ListUserInfoC2s vo) {

        PageInfo ret = userInfoService.selectByPage(vo.getPageNum(), vo.getPageSize());

        List<UserInfoVo> list = BeanMapper.mapList(ret.getList(), UserInfoVo.class);

        ListUserInfoS2c s2c = new ListUserInfoS2c();
        BeanMapper.copy(PageInfoUtil.s2c(ret),s2c);
        s2c.setList( list );

        return succRet(s2c);
    }

    @ApiOperation(value = "获取用户信息", nickname = "", response = UserInfoVo.class)
    @RequestMapping(method = RequestMethod.GET, path = "/infos/{id}" )
    public WebResp<S2cObj> get(@PathVariable("id") Long id) {

        UserInfo user = userInfoService.selectById( id );
        if(user == null) {
            return notFound();
        }
        return succRet( BeanMapper.map(user,UserInfoVo.class) );

    }

    @ApiOperation(value = "修改用户信息", nickname = "", response = UserInfoVo.class)
    @RequestMapping(method = RequestMethod.POST, path = "/infos/{id}" )
    public WebResp<S2cObj> update(@PathVariable("id") Long id, @RequestBody UserInfoVo vo) {

        UserInfo user = userInfoService.selectById( vo.getId() );
        if(user == null) {
            return notFound();
        }
        UserInfo info = BeanMapper.map(vo, UserInfo.class);
        userInfoService.updateByPrimaryKeySelective( info );
        return succRet( null );
    }


    @ApiOperation(value = "获取用户信息", nickname = "", response = UserInfoVo.class)
    @RequestMapping(method = RequestMethod.DELETE, path = "/infos/{id}" )
    public WebResp<S2cObj> del(@PathVariable("id") Long id) {

        int ret = userInfoService.deleteByPrimaryKey(id);
        if(ret == 1){
            return succRet(null);
        }else{
            return failReqRet();
        }

    }


}
