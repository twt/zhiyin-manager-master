package com.zhiyin.manager.module.address.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhiyin.dbs.module.address.entity.CustomAddressLocation;
import com.zhiyin.dbs.module.address.service.ICustomAddressLocationService;
import com.zhiyin.manager.module.address.module.*;
import com.zhiyin.manager.module.address.util.AddrLocUtil;
import com.zhiyin.manager.module.address.vo.PointVo;
import com.zhiyin.manager.module.common.controller.BaseController;
import com.zhiyin.manager.module.common.module.S2cObj;
import com.zhiyin.manager.web.entity.WebResp;
import com.zhiyin.utils.bean.BeanMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @author momo
 */

@Slf4j
@RestController
@RequestMapping({"/addrs/"})
@Api(value = "热点区域", description = "热点区域相关的CURD操作")
public class CustomAddressLocationController2 extends BaseController {

    @com.alibaba.dubbo.config.annotation.Reference
    private ICustomAddressLocationService customAddressLocationService;

    @ApiOperation(value = "查询热点的区域集合", nickname = "", response = CusAddrLocationS2c.class)
    @RequestMapping(value = "/{addrId}/locs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> getLocationByAddressId(@PathVariable Long addrId) {

        List<CustomAddressLocation> locationList = customAddressLocationService
                .selectByAddressId(addrId);
        List<CusAddrLocInfoS2c> list = BeanMapper.mapList(locationList, CusAddrLocInfoS2c.class);

        for (CusAddrLocInfoS2c tmp : list) {
            AddrLocUtil.convRec(tmp);
        }

        GetAddrCusAddrLocListS2c s2c = new GetAddrCusAddrLocListS2c( list );
        return succRet(s2c);
    }

    @ApiOperation(value = "查询热点的某个区域", nickname = "", response = CusAddrLocInfoS2c.class)
    @RequestMapping(value = "/{addrId}/locs/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> get( @PathVariable Long addrId,@PathVariable Long id ) {

        CustomAddressLocation loc = customAddressLocationService.selectById(id);

        CusAddrLocInfoS2c s2c = BeanMapper.map(loc,CusAddrLocInfoS2c.class);
        AddrLocUtil.convRec( s2c );

        return succRet(s2c);
    }

    @ApiOperation(value = "添加热点区域V2", nickname = "", response = WebResp.class)
    @RequestMapping(value = "/{addrId}/locs/v2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> add( @PathVariable Long addrId, @RequestBody CusAddrLocInfoV2C2s c2s ) {

        CustomAddressLocation cusAddrLoc = BeanMapper.map(c2s, CustomAddressLocation.class);

        cusAddrLoc.setAddressId(addrId);
        Long addRet = customAddressLocationService.insertSelectiveGet(cusAddrLoc);

        return succAddRet(addRet);
    }


    @ApiOperation(value = "添加热点区域", nickname = "", response = WebResp.class)
    @RequestMapping(value = "/{addrId}/locs", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> add( @PathVariable Long addrId, @RequestBody CusAddrLocInfoC2s c2s ) {

        CustomAddressLocation cusAddrLoc = BeanMapper.map(c2s, CustomAddressLocation.class);
        log.info("rectangle info:{}", JSON.toJSONString(c2s.getPointArray()));
        AddrLocUtil.setRectangle(c2s,cusAddrLoc);

        cusAddrLoc.setAddressId(addrId);
        Long addRet = customAddressLocationService.insertSelectiveGet(cusAddrLoc);

        return succAddRet(addRet);
    }

    @ApiOperation(value = "修改热点的某个区域", nickname = "", response = WebResp.class)
    @RequestMapping(value = "/{addrId}/locs/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> update( @PathVariable Long addrId, @PathVariable Long id, @RequestBody CusAddrLocInfoC2s c2s ) {

        CustomAddressLocation cusAddrLoc = BeanMapper.map(c2s, CustomAddressLocation.class);
        AddrLocUtil.setRectangle(c2s,cusAddrLoc);

        int ret = customAddressLocationService.updateByPrimaryKeySelective(cusAddrLoc);

        if(ret > 0 ){
            return succRet(null);
        }else{
            return failReqRet();
        }
    }

    @ApiOperation(value = "删除热点的某个区域", nickname = "", response = CusAddrLocationS2c.class)
    @RequestMapping(value = "/{addrId}/locs/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> del( @PathVariable Long addrId, @PathVariable Long id ) {

        int ret = customAddressLocationService.deleteByPrimaryKey(id);

        return succRet(null);
    }


}