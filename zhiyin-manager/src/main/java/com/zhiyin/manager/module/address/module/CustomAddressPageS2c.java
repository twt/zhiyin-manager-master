package com.zhiyin.manager.module.address.module;

import java.util.List;

import com.zhiyin.dbs.module.common.module.ResponsePageInfo;
import com.zhiyin.manager.module.common.module.S2cObj;

import lombok.Getter;
import lombok.Setter;

/** 
 * @author momo
 */
@Getter
@Setter
public class CustomAddressPageS2c extends S2cObj {
    
    private ResponsePageInfo pageInfo;
    
    private List<CustomAddressInfoS2c> customAddressList;

}
