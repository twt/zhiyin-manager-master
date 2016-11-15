package com.zhiyin.manager.module.data;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhiyin.dbs.factory.DubboServiceFactory;
import com.zhiyin.dbs.module.address.entity.AddressHotpic;
import com.zhiyin.dbs.module.address.entity.CustomAddress;
import com.zhiyin.file.ServerUploadFile;
import com.zhiyin.file.qiniu.FileBucket;
import com.zhiyin.file.qiniu.UploadFileRet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by hg on 2016/6/5.
 */
@Slf4j
public class AddrPicImport {

    public static Boolean test = false;

    public static void main(String[] args) throws IOException {

        String baseDir = "C:\\Users\\hg\\Desktop\\data\\addr_pic";
        baseDir = "C:\\Users\\hg\\Desktop\\20161108更新\\乾隆\\图片及简介";
        baseDir = "C:\\Users\\hg\\Desktop\\20161108更新\\韦小宝\\图片及简介";
        baseDir = "C:\\Users\\hg\\Desktop\\20161108更新\\小乔\\图片及简介";


        File baseFile = new File(baseDir);
        File[] files = baseFile.listFiles();
        for(File addrFile : files){

            log.info("addr name:{}",addrFile.getName());

            boolean parSonSame = true;
            // 文件夹下是否有子热点
            File[] subFiles = addrFile.listFiles();
            for(File subFile : subFiles){
                if(subFile.isDirectory()){
                    boolean ret = addPic(addrFile.getName(),subFile, 6);
                    parSonSame = false;
                    if(ret == false){
                        System.exit(1);
                    }
                }
            }

            if(parSonSame){
                boolean ret = addPic("北京市",addrFile, 5);
                if(ret == false){
                    System.exit(1);
                }
            }
        }

        System.out.println( "add addr pic succ end!" );
    }

    public static boolean addPic(String parAddrName,File addrFile, int degree) throws IOException {

        // 热点
        CustomAddress addr = AddrParseUtil.selectAddr(parAddrName, addrFile.getName(), degree);
        Long addrId = addr.getId();
        List<Long> addrIdList = DubboServiceFactory.customAddressService().selectSonId(addrId);
        if(addrIdList==null){
            addrIdList = Lists.newArrayList();
        }
        addrIdList.add(addrId);

        log.info("add addr pic,idList:{}",JSON.toJSONString(addrIdList));

        // 设置热点简介
        String[] txt = {"txt"};
        Collection<File> txtList = FileUtils.listFiles(addrFile, txt , false);
        if(txtList==null || txtList.size()==0){
            log.error("text file not exist. {}",addrFile.getName());
            return false;
        }
        String desc = FileUtils.readFileToString(txtList.iterator().next(),"GBK");
        log.info("addr {} desc {}",addrFile.getName(),desc);
        for(Long tmpId : addrIdList){
            if(!test){
                CustomAddress ca = new CustomAddress();
                ca.setId(tmpId);
                ca.setDescription(desc);
                DubboServiceFactory.customAddressService().updateByPrimaryKeySelective(ca);
            }
        }

        // 删除旧图片数据
        for(Long tmpId : addrIdList){
            if(!test){
                DubboServiceFactory.addressHotpicService().deleteRealByAddrId(tmpId);
            }
        }

        String[] jpg = {"jpg","JPG"};
        Collection<File> jpgs = FileUtils.listFiles(addrFile, jpg , false);
        for(File pic : jpgs) {
            if(pic.getName().endsWith("JPG")){
                System.out.println(pic.getPath());
            }

            // 上传图片
            UploadFileRet uploadRet = new UploadFileRet();
            if(test){
                uploadRet.setName(pic.getName());
            }else{
                uploadRet = ServerUploadFile.upload(FileBucket.AddrPic.getValue(), pic);
            }

            for(Long tmpId : addrIdList){
                AddressHotpic addrPic = new AddressHotpic();
                addrPic.setAddrId(tmpId);
                addrPic.setPicUrl(uploadRet.getName());
                log.info( tmpId  + "\t"+ uploadRet.getName() );
                if(!test){
                    DubboServiceFactory.addressHotpicService().insertSelective(addrPic);
                }
            }
        }
        return true;
    }

}
