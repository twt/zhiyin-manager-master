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
 * 更新热点数据
 * Created by hg on 2016/6/5.
 */
@Slf4j
public class AddrUpdate{

    public static void main(String[] args) throws IOException {

        Boolean test = false;

        Map<String, Object> map = ExcelReader.getAddr();
        Map<String,AddrInfo> parMap = (Map<String,AddrInfo>) map.get("parMap");
        Map<String, ArrayList<AddrInfo>> parSonMap = (Map<String, ArrayList<AddrInfo>>) map.get("parSonMap");

        for(Map.Entry<String,AddrInfo> entry : parMap.entrySet() ){

            String pName = entry.getKey();
            AddrInfo pAddr = entry.getValue();

            CustomAddress pAddrInfo = conv(entry.getValue());

            Long parId = 1L;
            // 处理父热点，如果已经存在则取父id，不存在则添加
            CustomAddress selByName = AddrParseUtil.selectAddr("北京市", pName ,pAddr.getDegree());
            if(selByName!=null){
                parId = selByName.getId();
            }else{
                if(!test){
                    log.info("add " +JSON.toJSONString( pAddrInfo ) );
                    parId = DubboServiceFactory.customAddressService().insertSelectiveGet( pAddrInfo );
                }
            }

            ArrayList<AddrInfo> sonList = parSonMap.get( pName );
            for(AddrInfo ca : sonList){
                ca.setParentId(parId);

                CustomAddress sonAddrInfo = conv(ca);
                Long sonId = 1L;
                // 子热点如果存在则更新，不存在则添加
                CustomAddress sonSelByName = AddrParseUtil.selectAddr(pName,ca.getName() ,ca.getDegree());
                if(sonSelByName != null){
                    sonId = sonSelByName.getId();
                    sonAddrInfo.setId(sonId);
                    if(!test){
                        log.info("update son {} {} {}",pName,ca.getName(),JSON.toJSONString( sonAddrInfo ));
                        int resul = DubboServiceFactory.customAddressService().updateByPrimaryKeySelective(sonAddrInfo);
                        log.info("result {}",resul);
                    }
                }else{
                    if(!test){
                        log.info("add son {} {} {}",pName,ca.getName(),JSON.toJSONString( sonAddrInfo ));
                        sonId = DubboServiceFactory.customAddressService().insertSelectiveGet( sonAddrInfo );
                    }
                }

                // 删除热点区域
                if(!test) {
                    DubboServiceFactory.customAddressLocationService().deleteRealByAddrId(sonId);
                }

                for(CustomAddressLocation cl : ca.getLocList() ){
                    cl.setAddressId(sonId);
                    if(!test){
                        log.info("add addr loc {}",JSON.toJSONString(cl));
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
