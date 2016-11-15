package com.zhiyin.manager.module.content.module;

import com.zhiyin.manager.module.common.module.S2cPageObj;
import com.zhiyin.manager.module.content.vo.ContentGroupVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by hg on 2016/4/29.
 */
@Getter
@Setter
@ToString
public class ListContentGroupS2c extends S2cPageObj {

    private List<ContentGroupVo> list;

}
