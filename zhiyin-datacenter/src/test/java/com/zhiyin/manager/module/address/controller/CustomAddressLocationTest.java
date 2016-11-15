package com.zhiyin.manager.module.address.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.zhiyin.manager.module.address.module.CusAddrLocInfoC2s;
import com.zhiyin.manager.module.address.module.SelLocationC2s;
import com.zhiyin.manager.module.content.controller.ControllerTestBase;

/** 
 * @author momo
 */
public class CustomAddressLocationTest extends ControllerTestBase {
    
    @Test
    public void testSelect() throws Exception{
        SelLocationC2s c2s = new SelLocationC2s();
        c2s.setAddressId(101L);
//        c2s.setId(1391183236964352L);
        String url = "/addr/hotpot/loc/addrId";
        this.sendPost(url,c2s);
    }
    
    @Test
    public void testSelectByIds() throws Exception{
        SelLocationC2s c2s = new SelLocationC2s();
        List<Long> list = new ArrayList<Long>();
        list.add(1001L);
        list.add(1002L);
        list.add(1003L);
        c2s.setAddressIdList(list);
//        c2s.setId(1391183236964352L);
        String url = "/addr/hotpot/loc/addrIds";
        this.sendPost(url,c2s);
    }
    
//    @Test
//    public void testInsert() throws Exception{
//        CusAddrLocInfoC2s c2s = new CusAddrLocInfoC2s();
//        c2s.setAddressId(101L);
//        c2s.setRectangleX1(1.100000000001);
//        c2s.setRectangleX2(1.2000000000001);
//        c2s.setRectangleY1(2.10000000000000001);
//        c2s.setRectangleY2(2.200000000001);
//        c2s.setRectangleCoord(3);
//        String url = "/addr/hotpot/loc/add";
//        this.sendPost(url,c2s);
//    }
//
//    @Test
//    public void testUpdate() throws Exception{
//        CusAddrLocInfoC2s c2s = new CusAddrLocInfoC2s();
//        c2s.setId(1391898457931776L);
//        c2s.setAddressId(101L);
//        c2s.setRectangleX1(3.100000000002);
//        c2s.setRectangleX2(3.200000002);
//        c2s.setRectangleY1(4.1000000002);
//        c2s.setRectangleY2(4.2000000002);
//        c2s.setRectangleCoord(3);
//        String url = "/addr/hotpot/loc/update";
//        this.sendPost(url,c2s);
//    }
    
    @Test
    public void testDel() throws Exception {
        CusAddrLocInfoC2s c2s = new CusAddrLocInfoC2s();
        c2s.setId(1391898457931776L);
        String url = "/addr/hotpot/loc/del";
        this.sendPost(url,c2s);
    }

}
