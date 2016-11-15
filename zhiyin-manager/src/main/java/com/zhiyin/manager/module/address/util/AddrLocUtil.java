package com.zhiyin.manager.module.address.util;

import com.google.common.collect.Lists;
import com.zhiyin.dbs.factory.CoordServiceFactory;
import com.zhiyin.dbs.module.address.entity.CustomAddressLocation;
import com.zhiyin.lbs.conf.CoordinateSystem;
import com.zhiyin.lbs.coord.CoordCovert;
import com.zhiyin.lbs.entity.CoordPoint;
import com.zhiyin.manager.module.address.module.CusAddrLocInfoC2s;
import com.zhiyin.manager.module.address.module.CusAddrLocInfoS2c;
import com.zhiyin.manager.module.address.module.CustomAddressInfoS2c;
import com.zhiyin.manager.module.address.vo.PointVo;

import java.util.Collections;
import java.util.List;

/**
 * Created by hg on 2016/5/2.
 */
public class AddrLocUtil {

    public static void convCustomAddr( CustomAddressInfoS2c s2c){
        if(s2c.getCenterIsset() == 1){
            CoordPoint cp1 = CoordCovert.conv( s2c.getCenterLongitude(), s2c.getCenterLatitude(), s2c.getCenterCoord(), CoordinateSystem.GCJ02.getType());
            s2c.setCenterLongitude(cp1.getLng() );
            s2c.setCenterLatitude( cp1.getLat() );
        }
    }

    // 前端传过来一个数组，将它转化成热点的两个坐标
    public static void setRectangle(CusAddrLocInfoC2s c2s, CustomAddressLocation location){

        List<PointVo> points = Lists.newArrayList();
        Double[] pointArray = c2s.getPointArray();
//        for (int i = 0; i < pointArray.length; i++) {
//                points.add( new PointVo(pointArray[i][0],pointArray[i][1]));
//        }
        for (int i = 0; i < pointArray.length; i=i+2) {
            points.add( new PointVo(pointArray[i],pointArray[i+1]));
        }

        List<Double> lonList = Lists.newArrayList();
        List<Double> latList = Lists.newArrayList();
        for( PointVo tmp : points){
            lonList.add(tmp.getLon());
            latList.add(tmp.getLat());
        }
        Collections.sort(lonList);
        Collections.sort(latList);

        location.setRectangleX1( lonList.get(0) );
        location.setRectangleY1( latList.get(0) );
        location.setRectangleX2( lonList.get( lonList.size()-1 ) );
        location.setRectangleY2( latList.get( latList.size()-1 ) );
    }


    public static void convRec(CusAddrLocInfoS2c s2c){
        CoordPoint cp1 = CoordCovert.conv( s2c.getRectangleX1(), s2c.getRectangleY1(), s2c.getRectangleCoord(), CoordinateSystem.GCJ02.getType());
        CoordPoint cp2 = CoordCovert.conv( s2c.getRectangleX2(), s2c.getRectangleY2() , s2c.getRectangleCoord(), CoordinateSystem.GCJ02.getType());
        s2c.setRectangleX1(cp1.getLng() );
        s2c.setRectangleY1(cp1.getLat() );
        s2c.setRectangleX2(cp2.getLng() );
        s2c.setRectangleY2(cp2.getLat() );

        List<PointVo> points = rec(s2c.getRectangleX1(), s2c.getRectangleY1(), s2c.getRectangleX2(), s2c.getRectangleY2());
        s2c.setPoints( points );

        s2c.setPointArray(array(s2c.getRectangleX1(), s2c.getRectangleY1(), s2c.getRectangleX2(), s2c.getRectangleY2()));
    }

    public static Double[][] array(Double x1, Double y1, Double x2, Double y2){
        Double[][] pointArray = new Double[4][2];
        pointArray[0][0] = x1;
        pointArray[0][1] = y1;
        pointArray[1][0] = x1;
        pointArray[1][1] = y2;
        pointArray[2][0] = x2;
        pointArray[2][1] = y2;
        pointArray[3][0] = x2;
        pointArray[3][1] = y1;

        return pointArray;
    }

    public static List<PointVo> rec(Double x1, Double y1, Double x2, Double y2){

        List<PointVo> list = Lists.newArrayList();

        list.add( new PointVo(x1,y1));
        list.add( new PointVo(x1,y2));
        list.add( new PointVo(x2,y2));
        list.add( new PointVo(x2,y1));

        return list;

    }
}
