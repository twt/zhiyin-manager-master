package com.zhiyin.manager.module.content.module;


import com.zhiyin.manager.module.address.module.CustomAddressInfoS2c;
import com.zhiyin.manager.module.common.module.S2cObj;
import com.zhiyin.manager.module.content.vo.ContentGroupVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by hg on 2016/5/5.
 */
@Getter
@Setter
public class SelCgroupByAddrS2c extends S2cObj {

    List<ContentGroupVo> list;
}
