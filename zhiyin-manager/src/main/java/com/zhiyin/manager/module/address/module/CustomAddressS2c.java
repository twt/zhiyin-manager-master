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
public class CustomAddressS2c extends S2cObj{

    private List<CustomAddressInfoS2c> customAddressList;
}
