package com.zhiyin.manager.module.address.module;

import java.util.List;

import com.zhiyin.manager.module.common.module.S2cObj;

import com.zhiyin.manager.module.address.vo.PointVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author momo
 */

@Getter
@Setter
public class CusAddrLocInfoS2c extends S2cObj {
    
    @ApiModelProperty(notes = "热点区域id", dataType = "Long")
    private Long id;

    @ApiModelProperty(notes = "热点id", dataType = "Long")
    private Long addressId;
    
    @ApiModelProperty(notes = "区域左下角横坐标")
    private Double rectangleX1;

    @ApiModelProperty(notes = "区域左下角纵坐标")
    private Double rectangleY1;

    @ApiModelProperty(notes = "区域右上角横坐标")
    private Double rectangleX2;

    @ApiModelProperty(notes = "区域右上角纵坐标")
    private Double rectangleY2;

    private List<PointVo> points;
    private Double[][] pointArray = new Double[4][2];

    
//    @ApiModelProperty(notes = "添加时间")
//    private Date addTime;
//
//    @ApiModelProperty(notes = "添加用户")
//    private Long addUserId;
//
//    @ApiModelProperty(notes = "更新时间")
//    private Date updateTime;
//
//    @ApiModelProperty(notes = "更新用户", dataType = "Long")
//    private Long updateUserId;
    
    @ApiModelProperty(notes = "中心点坐标系")
    private Integer centerpointCoord;

    @ApiModelProperty(notes = "坐标系")
    private Integer rectangleCoord;

}
