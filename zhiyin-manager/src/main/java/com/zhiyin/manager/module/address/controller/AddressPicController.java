package com.zhiyin.manager.module.address.controller;



import com.zhiyin.dbs.module.address.entity.AddressHotpic;
import com.zhiyin.dbs.module.address.service.IAddressHotpicService;
import com.zhiyin.manager.module.address.module.AddAddrPicC2s;
import com.zhiyin.manager.module.address.module.GetAddrPicS2c;
import com.zhiyin.manager.module.address.module.UpdateAddrPicC2s;
import com.zhiyin.manager.module.address.vo.AddressHotpicVo;
import com.zhiyin.manager.module.common.controller.BaseController;
import com.zhiyin.manager.module.common.module.AddSuccS2c;
import com.zhiyin.manager.module.common.module.S2cObj;
import com.zhiyin.manager.web.entity.WebResp;
import com.zhiyin.utils.bean.BeanMapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/addrs")
public class AddressPicController extends BaseController {

    @com.alibaba.dubbo.config.annotation.Reference
	private IAddressHotpicService addressHotpicService;

    @ApiOperation(value = "根据id查询热点图片", nickname = "", response = AddSuccS2c.class)
    @RequestMapping(value = "/pics/id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> getById(@PathVariable Long id ) {

        AddressHotpic pic = addressHotpicService.selectById(id);
        AddressHotpicVo s2c = BeanMapper.map(pic, AddressHotpicVo.class);

        return succRet(s2c);
    }


    @ApiOperation(value = "获取热点所有图片", nickname = "", response = AddSuccS2c.class)
    @RequestMapping(value = "/pics/{addrId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> getAll(@PathVariable Long addrId) {

        List<AddressHotpic> pics = addressHotpicService.selectAllByAddressId(addrId);

        List<AddressHotpicVo> list = BeanMapper.mapList(pics, AddressHotpicVo.class);

        GetAddrPicS2c s2c = new GetAddrPicS2c();
        s2c.setList(list);
        return succRet(s2c);
    }

    @ApiOperation(value = "增加热点图片", nickname = "", response = AddSuccS2c.class)
    @RequestMapping(value = "/pics/{addrId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> addPic(@PathVariable Long addrId, @Valid @RequestBody AddAddrPicC2s c2s ) {

        AddressHotpic pic = new AddressHotpic();
        pic.setAddrId(addrId);
        pic.setPicName(c2s.getFileName());
        Long id = addressHotpicService.insertSelectiveGet(pic);

        AddSuccS2c s2c = new AddSuccS2c();
        s2c.setId(id);

        return succRet(s2c);
    }

    @ApiOperation(value = "修改热点图片", nickname = "", response = WebResp.class)
    @RequestMapping(value = "/pics/{addrId}/{picId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> update(@PathVariable Long addrId, @PathVariable Long picId , @Valid @RequestBody UpdateAddrPicC2s c2s ) {

        AddressHotpic pic = new AddressHotpic();

        pic.setId(picId);
        pic.setPicName(c2s.getFileName());
        int ret = addressHotpicService.updateByPrimaryKeySelective(pic);
        if(ret == 1){
            return succRet(null);
        }else{
            return failReqRet();
        }
    }


    @ApiOperation(value = "删除热点图片", nickname = "", response = WebResp.class)
    @RequestMapping(value = "/pics/{addrId}/{picId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> del(@PathVariable Long addrId, @PathVariable Long picId, @Valid @RequestBody AddAddrPicC2s c2s ) {

        int ret = addressHotpicService.deleteByPrimaryKey(picId);
        if(ret == 1){
            return succRet(null);
        }else{
            log.error("删除图片失败");
            return failReqRet();
        }
    }

}
