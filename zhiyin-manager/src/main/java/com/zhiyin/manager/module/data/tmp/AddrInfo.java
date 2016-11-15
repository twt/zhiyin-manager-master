package com.zhiyin.manager.module.data.tmp;

import com.google.common.collect.Lists;
import com.zhiyin.dbs.module.address.entity.CustomAddress;
import com.zhiyin.dbs.module.address.entity.CustomAddressLocation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by hg on 2016/6/3.
 */
@Getter
@Setter
public class AddrInfo extends CustomAddress {

    private List<CustomAddressLocation> locList = Lists.newArrayList();
}
