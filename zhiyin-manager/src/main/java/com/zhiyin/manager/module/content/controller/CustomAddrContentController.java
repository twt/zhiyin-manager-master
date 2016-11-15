package com.zhiyin.manager.module.content.controller;

import java.util.List;

import com.zhiyin.dbs.module.content.service.IBasicContentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.zhiyin.dbs.module.content.service.ICustomaddressRoleContentService;
import com.zhiyin.manager.module.common.controller.BaseController;
import com.zhiyin.manager.module.common.module.S2cObj;
import com.zhiyin.manager.module.content.module.CustomAddrContentInfoS2c;
import com.zhiyin.manager.web.entity.WebResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/** 
 * 热点内容controller
 * @author momo
 */
@Slf4j
@RestController
@RequestMapping("/content/addr")
@Api(value = "热点内容", description = "热点内容的CURD操作")
public class CustomAddrContentController extends BaseController {

    @com.alibaba.dubbo.config.annotation.Reference
    private ICustomaddressRoleContentService customaddressRoleContentService;

    @com.alibaba.dubbo.config.annotation.Reference
    private IBasicContentService basicContentService;
    /**
     * 根据id查询
     * @param
     * @return
     */
//    @ApiOperation(value = "根据id查询", nickname = "", response = CustomAddrContentInfoS2c.class)
//    @RequestMapping(value = "/id", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    public WebResp<S2cObj> getById(@RequestParam Long id) {
//        if (id == null || id <= 0) {
//            log.info("req info not valid.");
//            return failReqRet();
//        }
//        CustomaddressRoleContent addrRoleContent = customaddressRoleContentService.selectById(id);
//
//        CustomAddrContentInfoS2c s2c = BeanMapper.map(addrRoleContent, CustomAddrContentInfoS2c.class);
//        return succRet(s2c);
//    }
    
    /**
     * 根据addressid查询
     * @param
     * @return
     */
    @ApiOperation(value = "查询热点的内容信息", nickname = "", response = CustomAddrContentInfoS2c.class)
    @RequestMapping(value = "/{addressId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> getByAddressId(@PathVariable Long addressId) {
        if (addressId == null || addressId <= 0) {
            log.info("req info not valid.");
            return failReqRet();
        }

        // 查询热点关联的内容
        List<Long> ids = customaddressRoleContentService.selectContentByAddress(addressId);


//        List<BasicContentVo>
//        for(Long id : ids){
//            basicContentService.selectById(id);
//        }
        
        CustomAddrContentInfoS2c s2c = new CustomAddrContentInfoS2c();
        s2c.setIds(ids);
        return succRet(s2c);
    }
    
}
