package com.zhiyin.manager.module.address.module;

import com.zhiyin.manager.module.common.module.S2cObj;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author momo
 */
@Getter
@Setter
public class ProvinceInfoS2c extends S2cObj {

    @ApiModelProperty(notes = "id", dataType = "Long")
    private Long id;

    @ApiModelProperty(notes = "名称")
    private String name;

}
