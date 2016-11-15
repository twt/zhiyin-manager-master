//package com.zhiyin.manager.module.address.controller;
//
//import com.google.common.collect.Lists;
//import com.zhiyin.dbs.module.address.entity.CustomAddressLocation;
//import com.zhiyin.dbs.module.address.service.ICustomAddressLocationService;
//import com.zhiyin.manager.module.address.module.CusAddrLocInfoC2s;
//import com.zhiyin.manager.module.address.module.CusAddrLocInfoS2c;
//import com.zhiyin.manager.module.address.module.CusAddrLocationS2c;
//import com.zhiyin.manager.module.address.module.Point;
//import com.zhiyin.manager.module.address.util.AddrLocUtil;
//import com.zhiyin.manager.module.common.controller.BaseController;
//import com.zhiyin.manager.module.common.module.S2cObj;
//import com.zhiyin.manager.web.entity.WebResp;
//import com.zhiyin.utils.bean.BeanMapper;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.PostConstruct;
//import java.util.Collections;
//import java.util.List;
//
///**
// * @author momo
// */
//
//@Slf4j
//@RestController
//@RequestMapping({"/address/loc" })
//@Api(value = "热点区域", description = "热点区域相关的CURD操作")
//public class CustomAddressLocationController extends BaseController {
//
//    @com.alibaba.dubbo.config.annotation.Reference
//    private ICustomAddressLocationService customAddressLocationService;
//
//
//    @ApiOperation(value = "查询热点的区域集合", nickname = "", response = CusAddrLocationS2c.class)
//    @RequestMapping(value = "/addrId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
//    public WebResp<S2cObj> getLocationByAddressId(@RequestParam Long addressId) {
//        if (addressId != null && addressId > 0) {
//
//            List<CustomAddressLocation> locationList = customAddressLocationService
//                    .selectByAddressId(addressId);
//            List<CusAddrLocInfoS2c> list = Lists.newArrayList();
//            if (locationList.size() > 0)
//                list = BeanMapper.mapList(locationList, CusAddrLocInfoS2c.class);
//
//            for(CusAddrLocInfoS2c tmp : list ){
//                AddrLocUtil.convRec(tmp);
//            }
//
//            CusAddrLocationS2c s2c = new CusAddrLocationS2c();
//            s2c.setCustomAddressLocationList(list);
//            return succRet(s2c);
//
//        } else {
//            log.info("req info not valid.");
//            return failReqRet();
//        }
//    }
//
//
//    @ApiOperation(value = "根据热点id集合查询location", nickname = "", response = CusAddrLocationS2c.class)
//    @RequestMapping(value = "/addrIds", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    public WebResp<S2cObj> getLocationByAddressIdList(@RequestParam List<Long> ids) {
//        if (ids != null && ids.size() > 0) {
//            List<CustomAddressLocation> locationList = customAddressLocationService
//                    .selectByAddressIdList(ids);
//            List<CusAddrLocInfoS2c> list = Lists.newArrayList();
//            if (locationList.size() > 0)
//                list = BeanMapper.mapList(locationList, CusAddrLocInfoS2c.class);
//            CusAddrLocationS2c s2c = new CusAddrLocationS2c();
//            s2c.setCustomAddressLocationList(list);
//            return succRet(s2c);
//        } else {
//            log.info("req info not valid.");
//            return failReqRet();
//        }
//    }
//
//    @ApiOperation(value = "查询热点区域", nickname = "", response = CusAddrLocInfoS2c.class)
//    @RequestMapping(value = "/id", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
//    public WebResp<S2cObj> getLocationById(@RequestParam Long id) {
//        if (id != null && id > 0) {
//            CustomAddressLocation customAddressLocation = customAddressLocationService.selectById(id);
//            CusAddrLocInfoS2c s2c = BeanMapper.map(customAddressLocation, CusAddrLocInfoS2c.class);
//            AddrLocUtil.convRec(s2c);
//            return succRet(s2c);
//        } else {
//            log.info("req info not valid.");
//            return failReqRet();
//        }
//    }
//
//    /**
//     * 增加location
//     *
//     * @param c2s
//     * @return
//     */
//    @ApiOperation(value = "增加location", nickname = "")
//    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    public WebResp<S2cObj> addLocationById(@RequestBody CusAddrLocInfoC2s c2s) {
//
//        List<Point> points = c2s.getPoints();
//        if( points == null || points.size() <2 ){
//            return failRet("坐标数必须大于2");
//        }
//
//        List<Double> lonList = Lists.newArrayList();
//        List<Double> latList = Lists.newArrayList();
//        for( Point tmp : points){
//            lonList.add(tmp.getLon());
//            latList.add(tmp.getLat());
//        }
//        Collections.sort(lonList);
//        Collections.sort(latList);
//
//
//        c2s.setRectangleX1( lonList.get(0) );
//        c2s.setRectangleY1( latList.get(0) );
//
//        c2s.setRectangleX2( lonList.get( lonList.size()-1 ) );
//        c2s.setRectangleY2( latList.get( latList.size()-1 ) );
//
//        CustomAddressLocation cusAddrLoc = BeanMapper.map(c2s, CustomAddressLocation.class);
//        int flag = customAddressLocationService.insertSelective(cusAddrLoc);
//
//        if (flag > 0)
//            return succRet(null);
//        return failRet("区域添加失败");
//    }
//
//    /**
//     * 修改location
//     *
//     * @param c2s
//     * @return
//     */
//    @ApiOperation(value = "修改location", nickname = "")
//    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    public WebResp<S2cObj> updateLocationById(@RequestBody CusAddrLocInfoC2s c2s) {
//
//        CustomAddressLocation cusAddrLoc = BeanMapper.map(c2s, CustomAddressLocation.class);
//        if (cusAddrLoc.getId() == null || cusAddrLoc.getId() == 0)
//            return failReqRet();
//        int flag = customAddressLocationService.updateByPrimaryKeySelective(cusAddrLoc);
//
//        if (flag > 0)
//            return succRet(null);
//        return failRet("区域更新失败");
//    }
//
//    /**
//     * 根据id删除location
//     *
//     * @param id
//     * @return
//     */
//    @ApiOperation(value = "根据id删除location", nickname = "")
//    @RequestMapping(value = "/del", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
//    public WebResp<S2cObj> delLocationById(@RequestParam Long id) {
//        if (id == null || id == 0)
//            return failReqRet();
//        int flag = customAddressLocationService.deleteByPrimaryKey(id);
//
//        if (flag > 0)
//            return succRet(null);
//        return failRet("区域删除失败");
//    }
//}