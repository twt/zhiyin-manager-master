package com.zhiyin.manager.module.data;

import com.alibaba.fastjson.JSON;
import com.zhiyin.dbs.factory.DubboServiceFactory;
import com.zhiyin.dbs.module.address.entity.CustomAddress;
import com.zhiyin.dbs.module.address.entity.CustomAddressLocation;
import com.zhiyin.manager.module.data.tmp.AddrInfo;
import com.zhiyin.manager.module.data.tmp.ExcelReader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 更新热点区域信息
 * Created by hg on 2016/6/25.
 */
@Slf4j
@Deprecated
public class AddrLocUpdate {

    static Boolean test = false;

    public static void main(String[] args) throws IOException {

        Map<String, Object> map = ExcelReader.getAddr();
        Map<String,AddrInfo> parMap = (Map<String,AddrInfo>) map.get("parMap");
        Map<String, ArrayList<AddrInfo>> parSonMap = (Map<String, ArrayList<AddrInfo>>) map.get("parSonMap");

        for(Map.Entry<String,AddrInfo> entry : parMap.entrySet() ){

            String pName = entry.getKey();
            ArrayList<AddrInfo> sonList = parSonMap.get( pName );
            for(AddrInfo ca : sonList){

                // 获取热点
                List<CustomAddress> addrList = DubboServiceFactory.customAddressService().selectByNameDegree(ca.getName(),6);

                if(addrList==null || addrList.size()!=1){
                    log.error("addr size error,{}",ca.getName());
                    return;
                }
                CustomAddress addr = addrList.get(0);
                // 删除旧的Location
                if(!test){
                    DubboServiceFactory.customAddressLocationService().deleteRealByAddrId(addr.getId());
                }
                for(CustomAddressLocation cl : ca.getLocList() ){
                    cl.setAddressId( addr.getId() );
                    if(!test){
                        DubboServiceFactory.customAddressLocationService().insertSelectiveGet(cl);
                    }
                    System.out.println("update "+addr.getId() + " " + JSON.toJSONString(cl));
                }
            }
        }

        System.out.println("succ");

    }

}
