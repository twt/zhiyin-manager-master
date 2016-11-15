package com.zhiyin.manager.module.content.module;

import java.util.Date;
import java.util.List;

import com.zhiyin.manager.module.common.module.S2cObj;

import lombok.Getter;
import lombok.Setter;

/** 
 * @author momo
 */
@Getter
@Setter
public class CustomAddrContentInfoS2c extends S2cObj {
    
    private Long id;

    private Long contentId;

    private Integer contentGid;

    private Long roleId;

    private Long addressId;

    private Integer playPriority;

    private Integer difficultyDegree;

    private Date addTime;

    private Date updateTime;

    private Long addUserId;

    private Long updateUserId;
    
    private List<Long> ids;

}
