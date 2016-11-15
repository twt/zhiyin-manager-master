package com.zhiyin.manager.module.content.controller;

import com.zhiyin.manager.module.content.module.AddrContentDownloadC2s;
import com.zhiyin.manager.module.content.module.SelContentGroupC2s;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hg on 2016/4/3.
 */
public class AddrContentDownloadControllerTest extends ControllerTestBase {


    @Test
    public void testUpdate() throws Exception {
        AddrContentDownloadC2s c2s = new AddrContentDownloadC2s();

        c2s.setAddressId(1021L);
        c2s.setIsFull(0);
        String url = "/content/download/update";
        this.sendPost(url, c2s);
    }
}