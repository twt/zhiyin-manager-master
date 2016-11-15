package com.zhiyin.manager.module.content.module;

import java.util.Date;

import com.zhiyin.manager.module.common.module.C2sObj;

import lombok.Getter;
import lombok.Setter;

/** 
 * @author momo
 */
@Getter
@Setter
public class SelContentGroupC2s extends C2sObj {

    private Long id;

    private String title;

    private String description;

    private Long roleId;

    private String keyword;

    private Date createTime;

    private Date updateTime;

    private Long addUserId;

    private Long updateUserId;
    
    private int s;
    
    private int p;
}
