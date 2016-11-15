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
public class CustomAddressInfoC2s extends C2sObj {

    @ApiModelProperty(notes = "id，新增时非必须，更新时必须", required = false)
    private Long id;

    @ApiModelProperty(notes = "热点等级", required = false)
    private Integer degree;

//    @ApiModelProperty(notes = "", required = false)
//    private String mapUserLocationPicture;

    @ApiModelProperty(notes = "热点名", required = false)
    private String name;

    @ApiModelProperty(notes = "全名", required = false)
    private String fullname;

    @ApiModelProperty(notes = "昵称", required = false)
    private String nickname;

    @ApiModelProperty(notes = "热点父id", required = false)
    private Long parentId;

    @ApiModelProperty(notes = "热点描述", required = false)
    private String description;

//    @ApiModelProperty(notes = "用户id", required = false)
//    private Long addUserId;

    @ApiModelProperty(notes = "中心点经度", required = false)
    private Double centerLongitude;

    @ApiModelProperty(notes = "中心点纬度", required = false)
    private Double centerLatitude;

    @ApiModelProperty(notes = "中心点是否设置", required = false)
    private Integer centerIsset;

    @ApiModelProperty(notes = "坐标系", required = false)
    private Integer centerCoord;

    @ApiModelProperty(notes = "所属城市id", required = false)
    public Long cityId;

}
