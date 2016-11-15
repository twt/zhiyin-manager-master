package com.zhiyin.manager.module.address.module;

import java.util.List;

import com.zhiyin.manager.module.common.module.S2cObj;

import lombok.Getter;
import lombok.Setter;

/**
 * @author momo
 */

@Getter
@Setter
public class CusAddrLocationS2c extends S2cObj {

    private List<CusAddrLocInfoS2c> customAddressLocationList;

}
