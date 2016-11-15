package com.zhiyin.manager.module.address.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.zhiyin.dbs.module.address.entity.CustomAddress;
import com.zhiyin.dbs.module.address.service.ICustomAddressService;
import com.zhiyin.dbs.module.common.module.ReqPageInfo;
import com.zhiyin.lbs.conf.CoordinateSystem;
import com.zhiyin.lbs.coord.CoordCovert;
import com.zhiyin.lbs.entity.CoordPoint;
import com.zhiyin.manager.module.address.module.CustomAddressInfoC2s;
import com.zhiyin.manager.module.address.module.CustomAddressInfoS2c;
import com.zhiyin.manager.module.address.module.CustomAddressPageS2c;
import com.zhiyin.manager.module.address.module.CustomAddressS2c;
import com.zhiyin.manager.module.address.util.AddrLocUtil;
import com.zhiyin.manager.module.common.controller.BaseController;
import com.zhiyin.manager.module.common.module.S2cObj;
import com.zhiyin.manager.util.PageInfoUtil;
import com.zhiyin.manager.web.entity.WebResp;
import com.zhiyin.utils.bean.BeanMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author momo
 */
@Slf4j
@RestController
@RequestMapping("/addrs")
@Api(value = "热点", description = "热点相关的CURD操作")
public class CustomAddressController extends BaseController {

//    @Autowired
    @com.alibaba.dubbo.config.annotation.Reference
    private ICustomAddressService customAddressService;

    /**
     * 查找一级热点
     */
    @ApiOperation(value = "获取一级热点", nickname = "", response = CustomAddressS2c.class)
    @RequestMapping(value = "/info/list", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> list( ) {
        List<CustomAddress> customAddressList = customAddressService.selectSon( 10L );
        List<CustomAddressInfoS2c> list = Lists.newArrayList();
        if (customAddressList.size() > 0)
            list = BeanMapper.mapList(customAddressList, CustomAddressInfoS2c.class);

        for(CustomAddressInfoS2c tmp : list){
            AddrLocUtil.convCustomAddr(tmp);
        }
        CustomAddressS2c s2c = new CustomAddressS2c();
        s2c.setCustomAddressList(list);
        return succRet(s2c);
    }

    /**
     * 查找下级热点
     */
    @ApiOperation(value = "查找所有子热点", nickname = "", response = CustomAddressS2c.class)
    @RequestMapping(value = "/info/{addrId}/son", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> getSonHotpot(@PathVariable Long addrId) {
        if (addrId == null) {
            log.info("req info not valid.");
            return failReqRet();
        }
        List<CustomAddress> customAddressList = customAddressService.selectSon(addrId);
        List<CustomAddressInfoS2c> list = Lists.newArrayList();
        if (customAddressList.size() > 0)
            list = BeanMapper.mapList(customAddressList, CustomAddressInfoS2c.class);

        for( CustomAddressInfoS2c tmp : list){
            AddrLocUtil.convCustomAddr(tmp);
        }

        CustomAddressS2c s2c = new CustomAddressS2c();
        s2c.setCustomAddressList(list);
        return succRet(s2c);
    }

    @ApiOperation(value = "获取热点信息", nickname = "", response = CustomAddressInfoS2c.class)
    @RequestMapping(value = "/info/{addrId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> get(@PathVariable Long addrId) {
        if ( addrId == null || addrId == null) {
            log.info("req info not valid.");
            return failReqRet();
        }
        CustomAddress customAddress = customAddressService.selectById(addrId);

        CustomAddressInfoS2c s2c = BeanMapper.map(customAddress, CustomAddressInfoS2c.class);

        AddrLocUtil.convCustomAddr(s2c);


        return succRet(s2c);
    }

    @ApiOperation(value = "添加热点", nickname = "", response = WebResp.class)
    @RequestMapping(value = "/info", method = RequestMethod.POST, produces =  MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> add( @RequestBody CustomAddressInfoC2s c2s ) {
        CustomAddress customAddress = BeanMapper.map(c2s, CustomAddress.class);
        long flag = customAddressService.insertSelectiveGet(customAddress);
        if (flag > 0)
            return succAddRet(flag);
        return failRet("热点添加失败");
    }

    @ApiOperation( value = "更新热点", nickname = "", response = WebResp.class)
    @RequestMapping( value = "/info/{addrId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> update(@PathVariable Long addrId, @RequestBody CustomAddressInfoC2s c2s) {
        CustomAddress customAddress = BeanMapper.map(c2s, CustomAddress.class);
        if (customAddress.getId() == null || customAddress.getId() == 0) {
            log.info("req info not valid.");
            return failReqRet();
        }
        int flag = customAddressService.updateByPrimaryKeySelective(customAddress);
        if (flag > 0)
            return succRet(null);
        return failRet("热点更新失败");
    }

    @ApiOperation( value = "删除热点", nickname = "", response = WebResp.class )
    @RequestMapping( value = "/info/{addrId}", method = RequestMethod.DELETE, produces =  MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> del( @PathVariable Long addrId ) {

        if ( addrId == null || addrId == 0)
            return failReqRet();
        int flag = customAddressService.deleteByPrimaryKey( addrId );
        if (flag > 0){
            return succRet(null);
        }
        return failRet("热点删除失败");
    }

}



  /**
     * 查询某城市的热点信息
     *
     * @param cityId
     * @return
     */
//@ApiOperation(value = "获取城市的热点(目前只有北京)", nickname = "", response = CustomAddressS2c.class)
//@RequestMapping(value = "/hotpot", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//public WebResp<S2cObj> getHotpotByCity(@ApiParam(value = "城市id", name = "cityId", defaultValue = "1") @RequestParam Long cityId) {
//    if (cityId == null || cityId <= 0) {
//        log.info("req info not valid.");
//        return failReqRet();
//    }
//    List<CustomAddress> customAddressList = customAddressService.selectAll();
//    List<CustomAddressInfoS2c> list = Lists.newArrayList();
//    if (customAddressList.size() > 0) {
//        list = BeanMapper.mapList(customAddressList, CustomAddressInfoS2c.class);
//    }
//    CustomAddressS2c s2c = new CustomAddressS2c();
//    s2c.setCustomAddressList(list);
//    return succRet(s2c);
//}
//
//    /**
//     * 分页查询所有热点信息
//     *
//     * @param p
//     * @param s
//     * @return
//     */
//    @ApiOperation(value = "分页查询所有热点信息", nickname = "", response = CustomAddressPageS2c.class)
//    @RequestMapping(value = "/hotpot/page", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    public WebResp<S2cObj> getHotpotByPage(@ApiParam(value = "页码", name = "p", defaultValue = "1") @RequestParam int p, @ApiParam(value = "每页数量", name = "s", defaultValue = "10") @RequestParam int s) {
//        ReqPageInfo pageC2s = new ReqPageInfo();
//        pageC2s.setP(p);
//        pageC2s.setS(s);
//        ReqPageInfo page = PageInfoUtil.formatPage(pageC2s);
//        PageInfo<CustomAddress> result = customAddressService.selectAllByPage(page.getP(), page.getS());
//        CustomAddressPageS2c s2c = new CustomAddressPageS2c();
//        s2c.setPageInfo(PageInfoUtil.format(result));
//        s2c.setCustomAddressList(BeanMapper.mapList(result.getList(), CustomAddressInfoS2c.class));
//        return succRet(s2c);
//    }
//
//
//
//    /**
//     * 根据等级和城市id获取热点
//     *
//     * @param cityId
//     * @param degree
//     * @return
//     */
//    @ApiOperation(value = "根据等级degree和城市id获取热点", nickname = "", response = CustomAddressS2c.class)
//    @RequestMapping(value = "/city/hotpot/degree", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    public WebResp<S2cObj> getHotpotByDegreeAndCity(@RequestParam Long cityId, @RequestParam int degree) {
//        if (cityId == null || degree == 0) {
//            log.info("req info not valid.");
//            return failReqRet();
//        }
//        List<CustomAddress> customAddressList = customAddressService.selectByDegreeAndCityId(cityId, degree);
//        List<CustomAddressInfoS2c> list = Lists.newArrayList();
//        if (customAddressList.size() > 0)
//            list = BeanMapper.mapList(customAddressList, CustomAddressInfoS2c.class);
//        CustomAddressS2c s2c = new CustomAddressS2c();
//        s2c.setCustomAddressList(list);
//        return succRet(s2c);
//    }

