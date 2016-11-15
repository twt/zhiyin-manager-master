package com.zhiyin.manager.module.data;

import com.google.common.collect.Lists;
import com.zhiyin.dbs.factory.DubboServiceFactory;
import com.zhiyin.dbs.module.address.entity.CustomAddress;

import java.util.List;

/**
 * 删除热点数据
 * Created by hg on 2016/9/6.
 */
public class AddrDel {

    public static void main(String[] args) {
//        测试数据	新世纪百货
//        测试数据	望京南地铁站
//        测试数据	东煌酒店

        List<String> addrNameList = Lists.newArrayList();
        addrNameList.add("新世纪百货");
        addrNameList.add("望京南地铁站");
        addrNameList.add("东煌酒店");
        for (String s : addrNameList) {
            CustomAddress delAddr = AddrParseUtil.selectAddr("测试数据",s, 6);

            DubboServiceFactory.customAddressService().deleteByPrimaryKey( delAddr.getId() );
        }


    }
}
