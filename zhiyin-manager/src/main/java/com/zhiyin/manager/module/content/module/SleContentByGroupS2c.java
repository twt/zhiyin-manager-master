package com.zhiyin.manager.module.content.module;


import com.zhiyin.app.api.content.module.BasicContentS2c;
import com.zhiyin.manager.module.common.module.S2cObj;
import com.zhiyin.manager.module.content.vo.BasicContentInfoVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by hg on 2016/5/3.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SleContentByGroupS2c extends S2cObj {

    private List<BasicContentInfoVo> list;

}
