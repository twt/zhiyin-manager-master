package com.zhiyin.manager.module.content.vo;

import java.util.Date;

import com.zhiyin.manager.module.common.module.S2cObj;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hg on 2016/3/19.
 */
@Getter
@Setter
@ApiModel(description = "基本内容信息")
public class BasicContentInfoVo extends S2cObj {
    
    @ApiModelProperty(notes = "id", dataType = "Long")
    private Long id;

    @ApiModelProperty(notes = "")
    private String adminDescription;

    @ApiModelProperty(notes = "int型id")
    private Integer gid;

    @ApiModelProperty(notes = "标题")
    private String title;

    @ApiModelProperty(notes = "资源")
    private Integer source;

    @ApiModelProperty(notes = "时长")
    private Float duration;

    @ApiModelProperty(notes = "角色id", dataType = "Long")
    private Long roleId;

    @ApiModelProperty(notes = "关键词")
    private String keyword;

    @ApiModelProperty(notes = "播放等级")
    private Integer playPriority;

    @ApiModelProperty(notes = "困难程度")
    private Integer difficultyDegree;

    @ApiModelProperty(notes = "描述")
    private String description;

    @ApiModelProperty(notes = "标签")
    private String tag;

    @ApiModelProperty(notes = "保存位置")
    private String savePath;

    @ApiModelProperty(notes = "")
    private Integer fsType;

    @ApiModelProperty(notes = "章节数")
    private Integer chapterNum;

    @ApiModelProperty(notes = "文章id", dataType = "Long")
    private Long articleId;

//    private Date shelveOnTime;

//    private Date shelveOffTime;

//    @ApiModelProperty(notes = "创建用户", dataType = "Long")
//    private Long createUserId;
//
//    private Long updateUserId;
//
//    private Date createTime;
//
//    private Date expireTime;
//
//    private String expireReason;
//
//    private Long expireUserId;

}
