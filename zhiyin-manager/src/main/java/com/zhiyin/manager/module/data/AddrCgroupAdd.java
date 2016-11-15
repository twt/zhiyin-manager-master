package com.zhiyin.manager.module.data;

import com.alibaba.fastjson.JSON;
import com.zhiyin.dbs.factory.DubboServiceFactory;
import com.zhiyin.dbs.module.address.entity.CustomAddress;
import com.zhiyin.dbs.module.content.entity.AddrCgroup;
import com.zhiyin.dbs.module.content.entity.ContentGroup;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 在热点下面添加内容
 * Created by hg on 2016/6/9.
 */
@Slf4j
public class AddrCgroupAdd {

    static boolean test = false ;

    public static void main(String[] args) throws IOException {
        String baseDir = DataConfig.AddrContentPath;
        File baseFile = new File(baseDir);
        File[] files = baseFile.listFiles();
        boolean succ = true ;

        for (File f : files) {
            log.info("文件夹 {}",f.getName());

            boolean isSubAddr = false;
            File[] subFile = f.listFiles();
            for(File tmp : subFile){
                if(tmp.isDirectory()){
                    boolean ret = proc(f.getName(),tmp);
                    if (ret == false) {
                        succ = false;
                        System.exit(-1);
                    }
                    isSubAddr = true;
                }
            }

            if( !isSubAddr ){
                boolean ret = proc(f.getName(),f);
                if (ret == false) {
                    succ = false;
                    System.exit(-1);
                }
            }
        }

        if (succ) {
            System.out.println(" succ end!");
        } else {
            System.out.println("error end!");
        }

    }

    public static boolean proc(String parAddrName,File file){

        String name = file.getName();
        CustomAddress addr = AddrParseUtil.selectAddr(parAddrName,file.getName(),6);

        ContentGroup cg = ContentUtil.selCg(parAddrName,file.getName());
        Long addrId = addr.getId();
        Long contentId =cg.getId();
        // 删除热点关联的内容
        if( !test ){
            DubboServiceFactory.addrCgroupService().deleteByAddrId( addrId );
        }

        log.info("addr {} {} content {} {}",addr.getName(),addr.getId(),cg.getTitle(),cg.getId());

        AddrCgroup addrCgroup = new AddrCgroup();
        addrCgroup.setAddrId( addrId );
        addrCgroup.setCgroupId( contentId );

        if( !test ) {
            DubboServiceFactory.addrCgroupService().insertSelectiveGet(addrCgroup);
        }

        return true;
    }


}
