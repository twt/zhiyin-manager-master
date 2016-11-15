package com.zhiyin.manager.module.address.module;

import java.util.List;

import com.zhiyin.manager.module.common.module.C2sObj;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelLocationC2s extends C2sObj {

    private Long id;
    
    private Long addressId;
    
    private List<Long> addressIdList;
    
}
