package com.zhiyin.manager.module.content.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhiyin.app.api.content.module.ZipAddContentC2s;
import com.zhiyin.app.api.content.module.ZipAddContentS2c;
import com.zhiyin.dbs.module.address.entity.CustomAddress;
import com.zhiyin.dbs.module.address.service.ICustomAddressService;
import com.zhiyin.dbs.module.content.entity.AddrContentDownload;
import com.zhiyin.dbs.module.content.entity.BasicContent;
import com.zhiyin.dbs.module.content.service.IBasicContentService;
import com.zhiyin.dbs.module.content.service.ICustomaddressRoleContentService;
import com.zhiyin.file.qiniu.FileBucket;
import com.zhiyin.file.qiniu.FileBucketConvert;
import com.zhiyin.http.factory.HttpRequestFactory;
import com.zhiyin.manager.config.HttpUrlConfig;
import com.zhiyin.manager.module.common.module.S2cObj;
import com.zhiyin.manager.module.content.module.AddrContentDownloadC2s;
import com.zhiyin.manager.module.content.module.GenContentZipByAddrLevelC2s;
import com.zhiyin.manager.module.content.module.UpdateAddrContentDownloadC2s;
import com.zhiyin.manager.module.content.module.UpdateAddrContentDownloadS2c;
import com.zhiyin.manager.web.entity.WebResp;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zhiyin.dbs.module.content.service.IAddrContentDownloadService;
import com.zhiyin.manager.module.common.controller.BaseController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import java.util.List;

/**
 * 热点离线下载包
 * @author hg
 */
@Slf4j
@RestController
@RequestMapping("contents/")
@Api(value = "")
public class AddrContentDownloadController extends BaseController {

    @Autowired
    HttpUrlConfig httpUrlConfig;

    @com.alibaba.dubbo.config.annotation.Reference
    private IAddrContentDownloadService addrContentDownloadService;

    @com.alibaba.dubbo.config.annotation.Reference
    private ICustomAddressService customAddressService;
    @com.alibaba.dubbo.config.annotation.Reference
    private IBasicContentService basicContentService;
    @com.alibaba.dubbo.config.annotation.Reference
    private ICustomaddressRoleContentService customaddressRoleContentService;

    /**
     * 根据热点等级生成下载内容包
     * 20160522
     */
    @ApiOperation(value = "根据热点等级生成下载包，慎用", nickname = "", response = WebResp.class)
    @RequestMapping(value = "gen/level/zip", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> genByAddrLevel(  @RequestBody GenContentZipByAddrLevelC2s c2s ) {
        List<CustomAddress> addrs = customAddressService.selectByDegree(c2s.getAddrLevel());

        log.info("sel by addr level:{}, addr size:{}", c2s.getAddrLevel(), addrs.size());

        for(CustomAddress addr : addrs){
            UpdateAddrContentDownloadC2s req = new UpdateAddrContentDownloadC2s();
            req.setIsFull(c2s.getIsFull());
            req.setAddressId(addr.getId());

            try {
                HttpRequestFactory.post( httpUrlConfig.getZipAddrLevelContent(),req);
            } catch (Exception e) {
                log.error("gen addrlevel zip content error, addr:{}, req:{}",JSON.toJSONString(addr), JSON.toJSONString(req), e );
            }
        }

        return succRet(null);
    }

    /**
     * 更新下载内容信息
     * @return
     */
    @ApiOperation(value = "生成热点的离线下载包", nickname = "AddrContentDownloadUpdate", response = UpdateAddrContentDownloadS2c.class)
    @RequestMapping(value = "gen/addr/zip", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WebResp<S2cObj> update(
            @Valid @RequestBody UpdateAddrContentDownloadC2s module,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("not pass request valid, req info: {}.", JSON.toJSONString(bindingResult.getAllErrors()) );
            return failRet( bindingResult.getAllErrors().get(0).getDefaultMessage() );
        }

        if( module.getIsFull() == null){
            log.warn("set default isfull value");
            module.setIsFull(0);
        }

        CustomAddress address = customAddressService.selectById(module.getAddressId());
        // 根据热点查找内容详情
        List<String> contentPathList = Lists.newArrayList();
        List<BasicContent> allConList = Lists.newArrayList();

        List<CustomAddress> addrList = Lists.newArrayList();
        if(module.getIsFull() == 1){
            // 查询子节点，不包括自身
            log.info("gen full download package, sel son max 1 deep. ");
            addrList = customAddressService.selectSon(module.getAddressId());
            CustomAddress ca = new CustomAddress();
            ca.setId(module.getAddressId());
            addrList.add(ca);
        }else{
            addrList.add(address);
        }

        for(CustomAddress cd : addrList){
            List<Long> contentIdList = customaddressRoleContentService.selectContentByAddress(cd.getId());
            List<BasicContent> contentList = basicContentService.selectByIdList(contentIdList);
            allConList.addAll(contentList);
        }

        String zipAddrName = "zipcontent" + address.getId() +"_" + module.getIsFull() + ".zip";
        ZipAddContentC2s zipAddContentC2s = new ZipAddContentC2s();

        for( BasicContent tmp : allConList){
            contentPathList.add( FileBucketConvert.getUrl(FileBucket.ContentAudio) + tmp.getSavePath() );
        }
        zipAddContentC2s.setAddrConList(contentPathList);
        zipAddContentC2s.setDestBucket(FileBucketConvert.getSpace(FileBucket.ContentAddrZip).getBucket());
        zipAddContentC2s.setDestName( zipAddrName );
        zipAddContentC2s.setFromBucket( FileBucketConvert.getSpace(FileBucket.ContentAudio).getBucket() );


        try {
            HttpRequestFactory.post( httpUrlConfig.getZipContent(),zipAddContentC2s, ZipAddContentS2c.class);
        } catch (Exception e) {
            log.error("gen zip error,",e);
            // 必须返回，否则将更新错误的压缩文件
            return failRet(e.getMessage());
        }

        AddrContentDownload tmp = new AddrContentDownload();
        tmp.setAddressId(module.getAddressId());
        tmp.setDownloadUrl( FileBucketConvert.getUrl(FileBucket.ContentAddrZip) + zipAddrName );
        tmp.setIsFull(module.getIsFull());

        int updateResult = addrContentDownloadService.insertOrUpdate(tmp);
        log.info("update addrcontentdownload:{}",JSON.toJSONString(tmp));

        UpdateAddrContentDownloadS2c s2c = new UpdateAddrContentDownloadS2c();
        s2c.setStatus(updateResult);
        return succRet(s2c);
    }

}
