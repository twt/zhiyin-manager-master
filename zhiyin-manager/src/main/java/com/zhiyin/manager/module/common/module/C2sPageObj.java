package com.zhiyin.manager.module.common.module;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class C2sPageObj extends C2sObj{

    @ApiModelProperty(value = "分页大小" ,example = "20")
    private Integer pageSize = 20;
    @ApiModelProperty(value = "页码" ,example = "1")
    private Integer pageNum = 1;

}