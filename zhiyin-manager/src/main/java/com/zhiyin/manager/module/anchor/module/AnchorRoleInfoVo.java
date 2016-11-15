package com.zhiyin.manager.module.anchor.module;

import com.zhiyin.app.api.common.module.S2cBasicInfo;
import com.zhiyin.manager.module.common.module.S2cObj;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnchorRoleInfoVo extends S2cObj {

	@ApiModelProperty(notes = "序号" )
	private Long id;
	@ApiModelProperty(notes = "名称")
	private String name;
	@ApiModelProperty(notes = "描述")
	private String description;
	@ApiModelProperty(notes = "头像")
	private String avatar;
	@ApiModelProperty(notes = "音频")
	private String audio;


}