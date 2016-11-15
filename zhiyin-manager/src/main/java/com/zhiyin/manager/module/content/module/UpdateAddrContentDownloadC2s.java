package com.zhiyin.manager.module.content.module;

import com.zhiyin.manager.module.common.module.C2sObj;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 *
 * Created by hg on 2016/1/9.
 */
@Getter
@Setter
public class UpdateAddrContentDownloadC2s extends C2sObj {

    @Min(value = 1, message = "{common.parameter.invalid}")
    @ApiModelProperty(notes = "热点编号" )
    private Long addressId;
    
    @Min(value = 0, message = "{common.parameter.invalid}")
    @ApiModelProperty(notes = "是否为大热点打包" )
    private Integer isFull;



}
