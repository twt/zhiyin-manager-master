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
public class CityInfoS2c extends S2cObj {

    @ApiModelProperty(notes = "城市id", dataType = "Long")
    private Long id;

    @ApiModelProperty(notes = "索引号")
    private Integer indexNo;

    @ApiModelProperty(notes = "所属省份id")
    private Integer provinceId;

    @ApiModelProperty(notes = "名称")
    private String name;

}
