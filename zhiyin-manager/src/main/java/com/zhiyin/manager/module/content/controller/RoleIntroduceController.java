//package com.zhiyin.manager.module.content.controller;
//
//import com.zhiyin.dbs.module.content.entity.RoleIntroduce;
//import com.zhiyin.dbs.module.user.entity.UserInfo;
//import com.zhiyin.manager.module.common.module.S2cObj;
//import com.zhiyin.manager.module.user.vo.UserInfoVo;
//import com.zhiyin.manager.web.entity.WebResp;
//import com.zhiyin.utils.bean.BeanMapper;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.zhiyin.dbs.module.content.service.IRoleIntroduceService;
//import com.zhiyin.manager.module.common.controller.BaseController;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.List;
//
///**
// * 主播角色介绍
// * @author momo
// */
//@Slf4j
//@RestController
//@RequestMapping("contents/roleintro")
//public class RoleIntroduceController extends BaseController {
//
//    @com.alibaba.dubbo.config.annotation.Reference
//    private IRoleIntroduceService roleIntroduceService;
//
//
//    @ApiOperation(value = "获取用户信息", nickname = "", response = UserInfoVo.class)
//    @RequestMapping(method = RequestMethod.GET, path = "/{id}" )
//    public WebResp<S2cObj> get(@PathVariable("id") Long id) {
//
//        List<RoleIntroduce> tmp = roleIntroduceService.selectByRoleId(id);
//        if(user == null) {
//            return notFound();
//        }
//        return succRet( BeanMapper.map(user,UserInfoVo.class) );
//    }
//}
