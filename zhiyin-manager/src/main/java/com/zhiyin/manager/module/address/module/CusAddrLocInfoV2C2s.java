package com.zhiyin.manager.module.address.module;

import com.zhiyin.manager.module.common.module.C2sObj;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CusAddrLocInfoV2C2s extends C2sObj {

    private Long id;

    private Long addressId;

    private Double rectangleX1;

    private Double rectangleY1;

    private Double rectangleX2;

    private Double rectangleY2;

    private Integer rectangleCoord;

}
