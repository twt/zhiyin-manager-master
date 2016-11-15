package com.zhiyin.manager.module.address.controller;

import org.junit.Test;

import com.zhiyin.manager.module.address.module.SelCityC2s;
import com.zhiyin.manager.module.content.controller.ControllerTestBase;

/** 
 * @author momo
 */
public class CityControllerTest extends ControllerTestBase {
    
    @Test
    public void testSelectByProvince() throws Exception {
        SelCityC2s c2s = new SelCityC2s();
        c2s.setProvinceId(5L);
        
        String url = "/address/province/city";
        this.sendPost(url,c2s);
    }

}
