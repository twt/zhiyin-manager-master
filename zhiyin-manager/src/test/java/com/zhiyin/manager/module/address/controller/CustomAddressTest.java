package com.zhiyin.manager.module.address.controller;

import com.zhiyin.manager.module.address.module.CustomAddressInfoC2s;
import com.zhiyin.manager.module.address.module.SelCustomAddrC2s;
import com.zhiyin.manager.module.content.controller.ControllerTestBase;
import org.junit.Test;

/**
 * @author momo
 */
public class CustomAddressTest extends ControllerTestBase {

    @Test
    public void testSelectByCity() throws Exception {
        SelCustomAddrC2s c2s = new SelCustomAddrC2s();
        c2s.setCityId(1L);
        String url = "/address/city/hotpot/degree";
        this.sendPost(url, c2s);
    }

    @Test
    public void testSelectById() throws Exception {
        SelCustomAddrC2s c2s = new SelCustomAddrC2s();
        c2s.setId(101L);
        String url = "/address/city/hotpot/id";
        this.sendPost(url, c2s);
    }

    @Test
    public void testSelectSon() throws Exception {
        SelCustomAddrC2s c2s = new SelCustomAddrC2s();
        c2s.setParentId(101L);
        String url = "/address/"+10+"/son";
        this.sendGet(url, c2s);
    }

    @Test
    public void testSelectByPage() throws Exception {
        SelCustomAddrC2s c2s = new SelCustomAddrC2s();
        c2s.setP(2);
        c2s.setS(11);
        String url = "/address/hotpot/page";
        this.sendPost(url, c2s);
    }

    @Test
    public void testAdd() throws Exception {
        CustomAddressInfoC2s c2s = new CustomAddressInfoC2s();
        c2s.setId(1391891611566080L);
//        c2s.setAddUserId(1000L);
        c2s.setCenterCoord(3);
        c2s.setName("testtest");
        c2s.setCenterLatitude(1.111111111111);
        c2s.setCenterLongitude(2.2222222222);
        String url = "/address/hotpot/add";
        this.sendPost(url, c2s);
    }

    @Test
    public void testDelByPage() throws Exception {
        CustomAddressInfoC2s c2s = new CustomAddressInfoC2s();
        c2s.setId(1391895275692032L);
        String url = "/address/hotpot/del";
        this.sendPost(url, c2s);
    }

    @Test
    public void testGetByCityAndDegree() throws Exception {
        SelCustomAddrC2s c2s = new SelCustomAddrC2s();
        c2s.setCityId(1L);
        c2s.setDegree(5);
        String url = "/address/city/hotpot/degree";
        this.sendPost(url, c2s);
    }

}
