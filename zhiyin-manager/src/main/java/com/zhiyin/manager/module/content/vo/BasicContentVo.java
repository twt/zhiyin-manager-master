package com.zhiyin.manager.module.content.vo;

import java.util.List;

import com.zhiyin.manager.module.common.module.S2cObj;

import lombok.Getter;
import lombok.Setter;

/** 
 * @author momo
 */
@Getter
@Setter
@Deprecated
public class BasicContentVo extends S2cObj {

    List<BasicContentInfoVo> basicContentList;
    
    List<Integer> gids;
}
