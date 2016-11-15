package com.zhiyin.manager.module.data;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhiyin.dbs.factory.DubboServiceFactory;
import com.zhiyin.dbs.module.anchor.entity.AnchorRoleInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * Created by hg on 2016/6/9.
 */
@Slf4j
public class TestDubbo {

    public static void main(String[] args) throws IOException {

//        String s = "sd df dd ss";
//        System.out.println("split:"+ s.split("\\s+",2)[1]);
//        DubboServiceFactory.customAddressService().selectById(101L);

        List<Long> roleIds = Lists.newArrayList();
        roleIds.add(1L);
        List<AnchorRoleInfo> tmp = DubboServiceFactory.anchorRoleInfoService().selectByIds(roleIds);
        log.info(JSON.toJSONString(tmp));


        DubboServiceFactory.contentGroupService().deleteRealByPrimaryKey(79787792121856L);
    }
}