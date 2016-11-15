package com.zhiyin.manager.module.data;

import com.alibaba.fastjson.JSON;
import com.zhiyin.dbs.factory.DubboServiceFactory;
import com.zhiyin.dbs.module.address.entity.CustomAddress;
import com.zhiyin.dbs.module.content.entity.ContentGroup;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 检查目录下面的内容是否添加
 * Created by hg on 2016/6/9.
 */
@Slf4j
public class CheckAddrContent {
    static boolean test = false;

    public static void main(String[] args) throws IOException {
        String baseDir = DataConfig.AddrContentPath;
        File baseFile = new File(baseDir);
        File[] files = baseFile.listFiles();

        for (File f : files) {
            boolean isSubAddr = false;
            File[] subFile = f.listFiles();
            for(File tmp : subFile){
                if(tmp.isDirectory()){
                    proc(tmp);
                    isSubAddr = true;
                }
            }

            if( !isSubAddr ){
                proc( f );
            }
        }
    }

    public static void proc(File file) {
        String name = file.getName();
        List<CustomAddress> addrList = DubboServiceFactory.customAddressService().selectByNameDegree(name, 6);
        if (addrList == null || addrList.size() != 1) {
            log.error("addr size error,{}, {}", JSON.toJSONString(name), file.getPath());
            return;
        }

        List<ContentGroup> cgList = DubboServiceFactory.contentGroupService().selectByTitle(name);
        if(cgList ==null || cgList.size() !=1){
            log.error("cg error,{},{}",JSON.toJSONString(name),file.getPath());
            return;
        }

    }
}