package com.zhiyin.manager.module.content.controller;

import com.github.pagehelper.PageInfo;
import com.zhiyin.dbs.module.address.entity.CustomAddress;
import com.zhiyin.dbs.module.address.service.ICustomAddressService;
import com.zhiyin.dbs.module.content.entity.AddrCgroup;
import com.zhiyin.dbs.module.content.entity.ContentGroup;
import com.zhiyin.dbs.module.content.service.IAddrCgroupService;
import com.zhiyin.dbs.module.content.service.IContentGroupService;
import com.zhiyin.manager.module.address.module.CustomAddressInfoS2c;
import com.zhiyin.manager.module.address.util.AddrVoUtil;
import com.zhiyin.manager.module.common.controller.BaseController;
import com.zhiyin.manager.module.common.module.S2cObj;
import com.zhiyin.manager.module.content.module.ListContentGroupC2s;
import com.zhiyin.manager.module.content.module.ListContentGroupS2c;
import com.zhiyin.manager.module.content.module.SelAddrByCgroup;
import com.zhiyin.manager.module.content.module.SelCgroupByAddrS2c;
import com.zhiyin.manager.module.content.vo.AddrCgroupVo;
import com.zhiyin.manager.module.content.vo.ContentGroupVo;
import com.zhiyin.manager.util.PageInfoUtil;
import com.zhiyin.manager.web.entity.WebResp;
import com.zhiyin.utils.bean.BeanMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hg on 2016/5/4.
 */
@Slf4j
@RestController
@RequestMapping("/contents")
@Api(value = "热点内容组", description = "热点内容组")
public class AddrContentGroupController extends BaseController {

    @com.alibaba.dubbo.config.annotation.Reference
    private IAddrCgroupService addrCgroupService;

    @com.alibaba.dubbo.config.annotation.Reference
    private IContentGroupService contentGroupService;

    @com.alibaba.dubbo.config.annotation.Reference
    private ICustomAddressService customAddressService;

    @ApiOperation(value = "热点关联的内容组", nickname = "", response = WebResp.class)
    @RequestMapping(value = "/addrs/{addrId}/cgroups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> ddd(@PathVariable Long addrId) {

        List<Long> cgroupList = addrCgroupService.selectCgroupByAddr(addrId);

        List<ContentGroup> groupList = contentGroupService.selectByIdList(cgroupList);

        SelCgroupByAddrS2c s2c = new SelCgroupByAddrS2c();
        s2c.setList(  BeanMapper.mapList( groupList, ContentGroupVo.class) );

        return succRet(s2c);
    }

    @ApiOperation(value = "内容组关联的热点", nickname = "", response = WebResp.class)
    @RequestMapping(value = "/cgroups/{cgroupId}/addrs" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> cgroupaddr(@PathVariable Long cgroupId) {

        List<Long> addrIdList = addrCgroupService.selectAddrByCgroup(cgroupId);

        List<CustomAddress> addrList = customAddressService.selectByIdList(addrIdList);

        List<CustomAddressInfoS2c> list = AddrVoUtil.addrInfo(addrList);

        SelAddrByCgroup s2c = new SelAddrByCgroup();
        s2c.setList(list);

        return  succRet(s2c);
    }

    @ApiOperation(value = "热点内容组添加", nickname = "", response = WebResp.class)
    @RequestMapping(value = "/addrcgroups", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> add(@RequestBody AddrCgroupVo c2s) {

        AddrCgroup addrCgroup = BeanMapper.map(c2s, AddrCgroup.class);
        long id = addrCgroupService.insertSelectiveGet(addrCgroup);

        if ( id > 0 ){
            return succAddRet(id);
        }

        return failRet("内容组添加失败");
    }

    @ApiOperation(value = "热点内容组删除", nickname = "", response = WebResp.class)
    @RequestMapping(value = "/addrcgroups/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> del(@PathVariable Long id) {

        int ret = addrCgroupService.deleteByPrimaryKey(id);

        if ( ret > 0 ){
            return succRet(null);
        }
        return failRet("内容组删除失败");
    }

}
