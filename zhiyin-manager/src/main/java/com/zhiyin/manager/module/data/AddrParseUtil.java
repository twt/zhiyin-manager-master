package com.zhiyin.manager.module.data;

import com.alibaba.fastjson.JSON;
import com.zhiyin.dbs.factory.DubboServiceFactory;
import com.zhiyin.dbs.module.address.entity.CustomAddress;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by hg on 2016/9/6.
 */
@Slf4j
public class AddrParseUtil {

    public static CustomAddress selectAddr(String parAddName, String addrName, Integer level){

        List<CustomAddress> parSelByName = DubboServiceFactory.customAddressService().selectByNameDegree( parAddName,level-1);
        if(parSelByName == null || parSelByName.size()>1){
            log.info("par addr too much. {}",parAddName);
            System.exit(-1);
        }

        CustomAddress parAddr = parSelByName.get(0);

        List<CustomAddress> selByName = DubboServiceFactory.customAddressService().selectByNameDegree( addrName,level);
        if(selByName == null || selByName.size() ==0){
            log.info("addr not found. {} {} ",parAddName,addrName);
//            System.exit(-1);
        }

        for (int i = 0; i < selByName.size(); i++) {
            if( selByName.get(i).getParentId().longValue() == parAddr.getId().longValue() ){
                return selByName.get(i);
            }
        }

        return null;
    }

    public static void main(String[] args) {

        CustomAddress sel = selectAddr("醇亲王府", "醇亲王府", 6);
        log.info(JSON.toJSONString(sel));
    }
}
