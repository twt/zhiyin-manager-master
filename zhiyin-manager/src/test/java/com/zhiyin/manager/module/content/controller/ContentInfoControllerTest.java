package com.zhiyin.manager.module.content.controller;

import com.zhiyin.manager.module.content.module.SelBasicContentC2s;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Created by hg on 2016/3/19.
 */
public class ContentInfoControllerTest extends ControllerTestBase {

    @Test
    public void testSel() throws Exception {
        SelBasicContentC2s c2s = new SelBasicContentC2s();
        c2s.setId(1001L);
        String url = "/content/basic/id";
        this.sendPost(url, c2s);
    }
    
    @Test
    public void testSelByGid() throws Exception {
        SelBasicContentC2s c2s = new SelBasicContentC2s();
        c2s.setGid(1001);
        String url = "/content/basic/gid";
        this.sendPost(url, c2s);
    }
    
    @Test
    public void testSelByArticalId() throws Exception {
        SelBasicContentC2s c2s = new SelBasicContentC2s();
        c2s.setArticleId(1011L);
        String url = "/content/basic/articleId";
        this.sendPost(url, c2s);
    }
    @Test
    public void testSelByIds() throws Exception {
        SelBasicContentC2s c2s = new SelBasicContentC2s();
        List<Long> ids = new ArrayList<Long>();
        ids.add(1001L);
        ids.add(1002L);
        c2s.setIds(ids);
        String url = "/content/basic/ids";
        this.sendPost(url, c2s);
    }
    
    @Test
    public void testSelGidByArticleId() throws Exception {
        SelBasicContentC2s c2s = new SelBasicContentC2s();
        c2s.setArticleId(1011L);
        String url = "/content/basic/gid/articleId";
        this.sendPost(url, c2s);
    }
    // TODO 出错
    @Test
    public void testAdd() throws Exception {
        SelBasicContentC2s c2s = new SelBasicContentC2s();
        c2s.setTitle("test");
        c2s.setDescription("testdesc");
        String url = "/content/basic/add";
        this.sendPost(url, c2s);
    }
    
    @Test
    public void testUpdate() throws Exception {
        SelBasicContentC2s c2s = new SelBasicContentC2s();
        c2s.setId(1000066L);
        c2s.setDescription("testdesc");
        String url = "/content/basic/update";
        this.sendPost(url, c2s);
    }
    
    @Test
    public void testDel() throws Exception {
        SelBasicContentC2s c2s = new SelBasicContentC2s();
        c2s.setId(1000066L);
        String url = "/content/basic/del";
        this.sendPost(url, c2s);
    }
}