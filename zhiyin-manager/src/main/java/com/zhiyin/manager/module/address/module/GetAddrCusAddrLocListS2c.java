package com.zhiyin.manager.module.address.module;

import com.zhiyin.manager.module.common.module.S2cObj;
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
public class GetAddrCusAddrLocListS2c extends S2cObj {

    public List<CusAddrLocInfoS2c> list;

}
