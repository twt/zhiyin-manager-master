package com.zhiyin.manager.module.address.controller.bak;

import com.zhiyin.dbs.module.address.entity.ProvinceInfo;
import com.zhiyin.dbs.module.address.service.IProvinceInfoService;
import com.zhiyin.manager.module.address.module.ProvinceInfoS2c;
import com.zhiyin.manager.module.address.module.ProvinceS2c;
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
//@RequestMapping("address")
//@Api(value = "省", description = "Province Controller")
public class ProvinceController extends BaseController {

    @com.alibaba.dubbo.config.annotation.Reference
    IProvinceInfoService provinceInfoService;

    /**
     * 查找所有的省
     * @return
     */
    @ApiOperation(value = "查找所有的省", nickname = "", response = ProvinceS2c.class)
    @RequestMapping(value = "/province", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public WebResp<S2cObj> getAll() {
        List<ProvinceInfo> list = provinceInfoService.selectAll();
        List<ProvinceInfoS2c> listS2c = BeanMapper.mapList(list, ProvinceInfoS2c.class);
        ProvinceS2c s2c = new ProvinceS2c();
        s2c.setList(listS2c);
        return succRet(s2c);
    }
    
    /**
     * 根据id查找省份
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查找省份", nickname = "", response = ProvinceInfoS2c.class)
    @RequestMapping(value = "/province/id", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public WebResp<S2cObj> getById(@RequestParam Long id) {
        ProvinceInfo provinceInfo = provinceInfoService.selectById(id);
        ProvinceInfoS2c s2c = BeanMapper.map(provinceInfo, ProvinceInfoS2c.class);
        return succRet(s2c);
    }
}
