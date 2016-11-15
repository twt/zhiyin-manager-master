package com.zhiyin.manager.module.address.module;

import com.zhiyin.manager.module.common.module.C2sObj;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author momo
 */
@Getter
@Setter
public class SelCustomAddrC2s extends C2sObj {
    
    @ApiModelProperty(notes = "热点id", dataType = "Long")
    private Long id;

    @ApiModelProperty(notes = "城市id", dataType = "Long")
    private Long cityId;
    
    @ApiModelProperty(notes = "中心点是否设置")
    private Integer centerIsset;
    
    @ApiModelProperty(notes = "热点等级")
    private Integer degree;
    
    @ApiModelProperty(notes = "父热点", dataType = "Long")
    private Long parentId;
    
    @ApiModelProperty(notes = "页码")
    private int p;

    @ApiModelProperty(notes = "每页条目数量")
    private int s;
}
