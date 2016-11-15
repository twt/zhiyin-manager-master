package com.zhiyin.manager.module.data;

import com.alibaba.fastjson.JSON;
import com.zhiyin.dbs.factory.DubboServiceFactory;
import com.zhiyin.dbs.module.content.entity.ContentGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by hg on 2016/9/6.
 */
@Slf4j
public class ContentUtil {


    public static ContentGroup selCg(String parAddrName,String addrName){

        List<ContentGroup> cgList = DubboServiceFactory.contentGroupService().selectByTitle( parAddrName + "-"+addrName);
        if(cgList ==null || cgList.size() !=1){
            log.error("cg error,{}", JSON.toJSONString(cgList));
            System.exit(-1);
        }
        return cgList.get(0);
    }


    public static List<ContentGroup> selCgList(String parAddrName, String addrName){

        List<ContentGroup> cgList = DubboServiceFactory.contentGroupService().selectByTitle( parAddrName + "-"+addrName);
//        if(cgList ==null || cgList.size() !=1){
//            log.error("cg error,{}", JSON.toJSONString(cgList));
//            System.exit(-1);
//        }
        return cgList;
    }

}
