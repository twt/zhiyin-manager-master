package com.zhiyin.manager.module.address.controller.bak;


import com.zhiyin.dbs.module.address.entity.CityInfo;
import com.zhiyin.dbs.module.address.service.ICityInfoService;
import com.zhiyin.manager.module.address.module.CityInfoS2c;
import com.zhiyin.manager.module.address.module.CitysS2c;
import com.zhiyin.manager.module.common.controller.BaseController;
import com.zhiyin.manager.module.common.module.S2cObj;
import com.zhiyin.manager.web.entity.WebResp;
import com.zhiyin.utils.bean.BeanMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author momo
 */
@Slf4j
//@RestController
//@RequestMapping("/address")
//@Api(value = "城市", description = "City Controller")
public class CityController extends BaseController {

    //@Autowired
    @com.alibaba.dubbo.config.annotation.Reference
    private ICityInfoService cityInfoService;

    /**
     * 列出某省下的城市
     *
     * @param provinceId province id
     * @return
     */
    @ApiOperation(value = "获取省下所有城市", nickname = "", response = CitysS2c.class)
    @RequestMapping(value = "/province/city", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public WebResp<S2cObj> getByProvince(@RequestParam Long provinceId) {
        if (provinceId == null || provinceId <= 0) {
            log.info("req info not valid.");
            return failReqRet();
        }

        List<CityInfo> cityInfoList = cityInfoService.selectByProvince(provinceId);

        List<CityInfoS2c> cityInfoS2cList = null;
        if (cityInfoList.size() > 0) {
            cityInfoS2cList = BeanMapper.mapList(cityInfoList, CityInfoS2c.class);
        }

        CitysS2c s2c = new CitysS2c();
        s2c.setCityList(cityInfoS2cList);
        return succRet(s2c);
    }
}
