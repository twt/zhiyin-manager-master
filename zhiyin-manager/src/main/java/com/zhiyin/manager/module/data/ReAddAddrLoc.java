package com.zhiyin.manager.module.data;

import com.alibaba.fastjson.JSON;
import com.zhiyin.dbs.factory.DubboServiceFactory;
import com.zhiyin.dbs.module.address.entity.CustomAddress;
import com.zhiyin.dbs.module.address.entity.CustomAddressLocation;
import com.zhiyin.manager.module.data.tmp.AddrInfo;
import com.zhiyin.manager.module.data.tmp.ExcelReader;
import com.zhiyin.utils.bean.BeanMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hg on 2016/6/5.
 */
@Slf4j
public class ReAddAddrLoc {

    public static void main(String[] args) throws IOException {

        Boolean test = false;

        Map<String, Object> map = ExcelReader.getAddr();
        Map<String,AddrInfo> parMap = (Map<String,AddrInfo>) map.get("parMap");
        Map<String, ArrayList<AddrInfo>> parSonMap = (Map<String, ArrayList<AddrInfo>>) map.get("parSonMap");

        for(Map.Entry<String,AddrInfo> entry : parMap.entrySet() ){

            String pName = entry.getKey();

            ArrayList<AddrInfo> sonList = parSonMap.get( pName );
            for(AddrInfo ca : sonList){

                List<CustomAddress> sonSelByName = DubboServiceFactory.customAddressService().selectByNameDegree( ca.getName(), 6 );
                if(sonSelByName==null || sonSelByName.size() != 1){
                    log.error("son addr error",JSON.toJSONString(ca.getName()));
                    return;
                }

                Long addrId = sonSelByName.get(0).getId();
                System.out.println( "address id:"+ ca.getName() + addrId);
                List<CustomAddressLocation> locList = DubboServiceFactory.customAddressLocationService().selectByAddressId(addrId);
                log.info(locList.size() + JSON.toJSONString(locList));
//                for(CustomAddressLocation tmp : locList){
//                    DubboServiceFactory.customAddressLocationService().del
//                }
                if(!test) {
                    DubboServiceFactory.customAddressLocationService().deleteRealByAddrId(addrId);
                }

                for(CustomAddressLocation cl : ca.getLocList() ){
                    cl.setAddressId( addrId );
                    if(!test){
                        DubboServiceFactory.customAddressLocationService().insertSelectiveGet(cl);
                    }
                }
            }
        }

        System.out.println("update addr loc succ!");
    }

    public static CustomAddress conv(AddrInfo info){
        return  BeanMapper.map(info,CustomAddress.class);
    }

}
