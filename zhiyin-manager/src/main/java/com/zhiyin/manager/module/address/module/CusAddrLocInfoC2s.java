package com.zhiyin.manager.module.address.module;

import com.zhiyin.manager.module.address.vo.PointVo;
import com.zhiyin.manager.module.common.module.C2sObj;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author momo
 */

@Getter
@Setter
public class CusAddrLocInfoC2s extends C2sObj {

    private Long id;
    
    private Long addressId;

//    private Double rectangleX1;
//
//    private Double rectangleY1;
//
//    private Double rectangleX2;
//
//    private Double rectangleY2;

//    private Long addUserId;
//
//    private Long updateUserId;

//    private Integer centerpointCoord;

    private Integer rectangleCoord;

//    private List<PointVo> points;

//    private Double[][] pointArray = new Double[4][2];

    private Double[] pointArray = new Double[8];

}
