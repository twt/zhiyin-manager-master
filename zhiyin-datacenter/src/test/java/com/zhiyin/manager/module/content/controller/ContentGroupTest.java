package com.zhiyin.manager.module.content.controller;

import org.junit.Test;

import com.zhiyin.manager.module.content.module.SelContentGroupC2s;

/** 
 * @author momo
 */
public class ContentGroupTest extends ControllerTestBase {
    
    @Test
    public void selByIdTest() throws Exception{
        SelContentGroupC2s c2s = new SelContentGroupC2s();
        c2s.setId(1395411542114304L);
        String url = "/content/group/id";
        this.sendPost(url,c2s);
    }
    
    @Test
    public void addTest() throws Exception{
        SelContentGroupC2s c2s = new SelContentGroupC2s();
        c2s.setTitle("test");
        c2s.setDescription("description");
        String url = "/content/group/add";
        this.sendPost(url,c2s);
    }
    
    @Test
    public void updateTest() throws Exception{
        SelContentGroupC2s c2s = new SelContentGroupC2s();
        c2s.setId(1395411542114304L);
        c2s.setTitle("test2");
        String url = "/content/group/update";
        this.sendPost(url,c2s);
    }
    
    @Test
    public void delTest() throws Exception{
        SelContentGroupC2s c2s = new SelContentGroupC2s();
        c2s.setId(1395411542114304L);
        String url = "/content/group/del";
        this.sendPost(url,c2s);
    }

}
