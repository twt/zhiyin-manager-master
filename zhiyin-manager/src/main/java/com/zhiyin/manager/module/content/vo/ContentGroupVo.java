package com.zhiyin.manager.module.content.vo;

import java.util.Date;

import com.zhiyin.manager.module.common.module.S2cObj;

import lombok.Getter;
import lombok.Setter;

/** 
 * @author momo
 */
@Getter
@Setter
public class ContentGroupVo extends S2cObj {
    
    private Long id;

    private String title;

    private String description;

    private Long roleId;

    private String roleName;

    private String keyword;

//    private Date createTime;

//    private Date updateTime;

//    private Long addUserId;
//
//    private Long updateUserId;

}
