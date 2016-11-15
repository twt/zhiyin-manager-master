package com.zhiyin.manager.module.data;

import com.alibaba.fastjson.JSON;
import com.zhiyin.dbs.factory.DubboServiceFactory;
import com.zhiyin.dbs.module.address.entity.CustomAddress;
import com.zhiyin.dbs.module.address.entity.CustomAddressLocation;
import com.zhiyin.http.factory.ApiCrudFactory;
import com.zhiyin.manager.module.address.module.CustomAddressInfoC2s;
import com.zhiyin.manager.module.data.tmp.AddrInfo;
import com.zhiyin.manager.module.data.tmp.ExcelReader;
import com.zhiyin.utils.bean.BeanMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 导入全新的热点数据
 * Created by hg on 2016/6/5.
 */
@Deprecated
@Slf4j
public class ImportAddrData {

    public static void main(String[] args) throws IOException {

        Boolean test = true;

        Map<String, Object> map = ExcelReader.getAddr();
        Map<String,AddrInfo> parMap = (Map<String,AddrInfo>) map.get("parMap");
        Map<String, ArrayList<AddrInfo>> parSonMap = (Map<String, ArrayList<AddrInfo>>) map.get("parSonMap");

        for(Map.Entry<String,AddrInfo> entry : parMap.entrySet() ){

            String pName = entry.getKey();
            AddrInfo pAddr = entry.getValue();

            // 热点已经添加需要先删除区域
            List<CustomAddress> selByName = DubboServiceFactory.customAddressService().selectByNameDegree( pName,pAddr.getDegree());
            if(selByName!=null && selByName.size()>0){
                log.error("par exist.{}",entry.getKey());
                for(CustomAddress tmp : selByName){
//                    DubboServiceFactory.customAddressService().deleteRealByPrimaryKey(tmp.getId());
                    DubboServiceFactory.customAddressLocationService().deleteRealByAddrId( tmp.getId() );
                }
            }
            Long parId = 1L;
            if(!test){
                log.info("add " +JSON.toJSONString(entry.getValue()));
                parId = DubboServiceFactory.customAddressService().insertSelectiveGet( conv(entry.getValue()) );
            }
            ArrayList<AddrInfo> sonList = parSonMap.get( pName );
            for(AddrInfo ca : sonList){
                ca.setParentId(parId);
                List<CustomAddress> sonSelByName = DubboServiceFactory.customAddressService().selectByNameDegree( ca.getName(),ca.getDegree() );
                if(sonSelByName!=null && sonSelByName.size()>0){
                    for(CustomAddress del : sonSelByName){
                        log.error("son exist.{} {}",ca.getName(), del.getId() );
                        DubboServiceFactory.customAddressService().deleteRealByPrimaryKey( del.getId());
                    }
                }

                Long sonId = 1L;
                if(!test){
                    sonId = DubboServiceFactory.customAddressService().insertSelectiveGet( conv(ca) );
                }
                for(CustomAddressLocation cl : ca.getLocList() ){
                    cl.setAddressId(sonId);
                    if(!test){
                        DubboServiceFactory.customAddressLocationService().insertSelectiveGet(cl);
                    }
                }
            }
        }

        System.out.println("end succ");
    }

    public static CustomAddress conv(AddrInfo info){
        return  BeanMapper.map(info,CustomAddress.class);
    }

}
