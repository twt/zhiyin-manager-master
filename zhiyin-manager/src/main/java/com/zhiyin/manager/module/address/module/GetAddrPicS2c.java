package com.zhiyin.manager.module.address.module;


import com.zhiyin.manager.module.address.vo.AddressHotpicVo;
import com.zhiyin.manager.module.common.module.S2cObj;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by hg on 2016/5/2.
 */
@Getter
@Setter
public class GetAddrPicS2c extends S2cObj {
    private List<AddressHotpicVo> list;
}
