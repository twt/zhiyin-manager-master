package com.zhiyin.manager.module.data;

import com.alibaba.fastjson.JSON;
import com.zhiyin.dbs.factory.DubboServiceFactory;
import com.zhiyin.dbs.module.address.entity.CustomAddress;
import com.zhiyin.manager.module.data.tmp.ExcelReader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * Created by hg on 2016/8/20.
 */
@Slf4j
@Deprecated
public class AddrOverlaySet {

    static boolean test = false;
    public static void main(String[] args) throws IOException {

        String path   =  "C:\\Users\\hg\\Desktop\\数据\\overlay.xlsx";
        ExcelReader reader = new ExcelReader(path);
        List<String[]> data = reader.getAllData(0);

//        log.info(JSON.toJSONString(data));
        for(String[] tmp : data){
            update(tmp[0],Integer.valueOf(tmp[1])) ;
        }

    }

    public static void update(String name,Integer overlay){

        List<CustomAddress> addrList = DubboServiceFactory.customAddressService().selectByNameDegree( name,6);
        if(addrList==null || addrList.size()!=1){
            log.error("addr size error,{}", name);
            System.exit(-1);
        }

        CustomAddress addr = addrList.get(0);
        addr.setOverlayDegree(overlay);
        log.info(JSON.toJSONString(addr));
        if(!test){
            DubboServiceFactory.customAddressService().updateByPrimaryKeySelective( addr );
        }
    }

}
