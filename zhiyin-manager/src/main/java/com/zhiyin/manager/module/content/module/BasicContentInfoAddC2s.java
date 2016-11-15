package com.zhiyin.manager.module.content.module;

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
public class BasicContentInfoAddC2s extends S2cObj {

//    @ApiModelProperty(notes = "管理员描述")
//    private String adminDescription;

    @ApiModelProperty(notes = "标题")
    private String title;

    @ApiModelProperty(notes = "音频时长")
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

//    @ApiModelProperty(notes = "标签")
//    private String tag;

    @ApiModelProperty(notes = "保存位置")
    private String savePath;

    @ApiModelProperty(notes = "内容组编号", dataType = "Long")
    private Long articleId;

}
