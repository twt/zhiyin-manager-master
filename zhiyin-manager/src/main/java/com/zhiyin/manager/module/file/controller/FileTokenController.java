package com.zhiyin.manager.module.file.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhiyin.dbs.module.content.service.IRoleIntroduceRecordService;
import com.zhiyin.file.FileTokenGen;
import com.zhiyin.file.FileUploadInfoC2s;
import com.zhiyin.file.qiniu.FileBucket;
import com.zhiyin.manager.module.common.controller.BaseController;
import com.zhiyin.manager.module.common.module.S2cObj;
import com.zhiyin.manager.module.file.module.GetFileBucketS2c;
import com.zhiyin.manager.module.file.module.GetFileUploadTokenC2s;
import com.zhiyin.manager.module.file.module.GetFileUploadTokenS2c;
import com.zhiyin.manager.module.file.module.GetJsFileUploadTokenC2s;
import com.zhiyin.manager.module.file.vo.FileBucketVo;
import com.zhiyin.manager.web.entity.WebResp;
import com.zhiyin.utils.bean.BeanMapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * */
@Slf4j
@RestController
@RequestMapping("/files")
public class FileTokenController extends BaseController {

    @com.alibaba.dubbo.config.annotation.Reference
    private IRoleIntroduceRecordService roleIntroduceRecordService;

    @ApiOperation(value = "文件类型", nickname = "", response = GetFileUploadTokenS2c.class)
    @RequestMapping( value = "/types", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> type( ) {

        List<FileBucketVo> list = Lists.newArrayList();
        FileBucket[] vals = FileBucket.values();
        for(FileBucket tmp : vals ){
            FileBucketVo vo = new FileBucketVo();
            vo.setName(tmp.getName());
            vo.setValue(tmp.getValue());
            list.add(vo);
        }

        GetFileBucketS2c s2c = new GetFileBucketS2c();
        s2c.setList(list);

        return succRet(s2c);
    }

    @ApiOperation(value = "获取文件上传Token", nickname = "", response = GetFileUploadTokenS2c.class)
    @RequestMapping( value = "/token", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> genToken( @RequestBody GetFileUploadTokenC2s c2s ) {

        FileUploadInfoC2s tmp = FileTokenGen.qiniuToken(c2s.getType(), c2s.getName());

        GetFileUploadTokenS2c s2c = BeanMapper.map(tmp, GetFileUploadTokenS2c.class);
        return succRet(s2c);
    }

    @ApiOperation(value = "获取JS文件上传Token", nickname = "", response = GetFileUploadTokenS2c.class)
    @RequestMapping( value = "/jstoken", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public WebResp<S2cObj> genjsToken( @RequestBody GetJsFileUploadTokenC2s c2s ) {

        FileUploadInfoC2s tmp = FileTokenGen.qiniuJsToken(c2s.getType() );
        log.info("upload token info:{}",JSON.toJSONString(tmp));
        GetFileUploadTokenS2c s2c = BeanMapper.map(tmp, GetFileUploadTokenS2c.class);
        return succRet(s2c);
    }




}
