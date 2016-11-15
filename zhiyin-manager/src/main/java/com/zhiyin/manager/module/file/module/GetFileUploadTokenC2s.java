package com.zhiyin.manager.module.file.module;

import com.zhiyin.manager.module.common.module.C2sObj;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/** 
 * @author hg
 */
@Getter
@Setter
public class GetFileUploadTokenC2s extends C2sObj {

//    @ApiModelProperty(notes = "文件服务提供商，可选", dataType = "String" )
//    private String provider; // 提供商

    @ApiModelProperty(notes = "文件类型", dataType = "String")
    private String type;

    @ApiModelProperty(notes = "文件名称", dataType = "String")
    private String name;


}
