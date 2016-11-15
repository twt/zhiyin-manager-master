package com.zhiyin.manager.module.anchor.module;


import com.zhiyin.manager.module.common.module.S2cObj;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GetAnchorRoleInfoS2c extends S2cObj {

    @ApiModelProperty(notes = "角色列表", dataType = "List")
    private List<AnchorRoleInfoVo> list;
//   private int currentPage;
//   private int pageSize;
//   private int totalPages;
}
