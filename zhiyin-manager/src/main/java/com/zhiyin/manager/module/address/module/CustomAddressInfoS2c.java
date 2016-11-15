package com.zhiyin.manager.module.address.module;

import java.util.Date;

import com.zhiyin.manager.module.common.module.S2cObj;

import lombok.Getter;
import lombok.Setter;

/**
 * @author momo
 */
@Getter
@Setter
public class CustomAddressInfoS2c extends S2cObj {

    private Long id;

    private Integer degree;
    
//    private String mapUserLocationPicture;

    private String name;

    private String fullname;

    private String nickname;

    private Long parentId;

    private String description;

//    private Date addTime;
//
//    private Long addUserId;
//
//    private Date updateTime;
//
//    private Long updateUserId;

    private Double centerLongitude;
    
    private Double centerLatitude;
    
    private Integer centerIsset;
    
    private Integer centerCoord;

}
