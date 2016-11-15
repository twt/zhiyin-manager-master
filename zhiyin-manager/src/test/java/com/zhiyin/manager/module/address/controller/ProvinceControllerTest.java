package com.zhiyin.manager.module.address.controller;

import org.junit.Test;

import com.zhiyin.manager.module.address.module.SelProvinceC2s;
import com.zhiyin.manager.module.content.controller.ControllerTestBase;

/** 
 * @author momo
 */
public class ProvinceControllerTest extends ControllerTestBase {

    @Test
    public void testSelectAll() throws Exception {
        String url = "/address/province";
        this.sendPost(url,null);
    }
    
    @Test
    public void testSelectById() throws Exception {
        String url = "/address/province/id";
        SelProvinceC2s c2s = new SelProvinceC2s();
        c2s.setId(2L);
        this.sendPost(url,c2s);
    }
}
