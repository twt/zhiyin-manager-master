package com.zhiyin.manager.module.address.util;

import com.zhiyin.dbs.module.address.entity.CustomAddress;
import com.zhiyin.manager.module.address.module.CustomAddressInfoS2c;
import com.zhiyin.utils.bean.BeanMapper;

import java.util.List;

/**
 * Created by hg on 2016/5/5.
 */
public class AddrVoUtil {

    public static CustomAddressInfoS2c addrInfo(CustomAddress tmp){

        return BeanMapper.map(tmp,CustomAddressInfoS2c.class);
    }

    public static List<CustomAddressInfoS2c> addrInfo(List<CustomAddress> tmp){

        return BeanMapper.mapList(tmp,CustomAddressInfoS2c.class);
    }

}
