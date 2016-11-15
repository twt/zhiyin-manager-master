package com.zhiyin.manager.module.data;

import com.alibaba.fastjson.JSON;
import com.zhiyin.dbs.factory.DubboServiceFactory;
import com.zhiyin.dbs.module.address.entity.AddrRoleMap;
import com.zhiyin.dbs.module.address.entity.CustomAddress;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by hg on 2016/8/25.
 */
@Slf4j
public class AddrRoleMapAdd {

    public static void main(String[] args) {
        List<CustomAddress> addrs = DubboServiceFactory.customAddressService().selectAll();

        for(CustomAddress addr : addrs){
            Long id = addr.getId();
            long role = 1L;
            if( addr.getId() == 50578407333888L ){
                role = 4L;
            }else if(addr.getId() == 50578399744000L){
              role = 3L;
            }else{
            }

            AddrRoleMap map = new AddrRoleMap();
            map.setAddrId(id);
            map.setRoleId(role);
            log.info(JSON.toJSONString(map));
            DubboServiceFactory.addrRoleMapService().insertSelectiveGet(map);
        }

        log.info("end");
    }
}
