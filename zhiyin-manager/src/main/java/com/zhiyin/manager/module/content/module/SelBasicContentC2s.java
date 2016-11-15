package com.zhiyin.manager.module.content.module;

import java.util.Date;
import java.util.List;

import com.zhiyin.manager.module.common.module.C2sObj;

import lombok.Getter;
import lombok.Setter;

/**
 * @author momo
 */
@Getter
@Setter
@Deprecated
public class SelBasicContentC2s extends C2sObj {
    
    private Long id;
    
    private Integer gid;
    
    private Long articleId;
    
    private String adminDescription;

    private String title;

    private Integer source;

    private Float duration;

    private Long roleId;

    private String keyword;

    private Integer playPriority;

    private Integer difficultyDegree;

    private String description;

    private String tag;

    private String savePath;

    private Integer fsType;

    private Integer chapterNum;

    private Date shelveOnTime;

    private Date shelveOffTime;

    private Long createUserId;

    private Long updateUserId;

    private Date createTime;

    private Date expireTime;

    private String expireReason;

    private Long expireUserId;
    
    private List<Long> ids;

}
