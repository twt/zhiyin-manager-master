package com.zhiyin.manager.module.data;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhiyin.audio.Mp3TimeUtil;
import com.zhiyin.dbs.factory.DubboServiceFactory;
import com.zhiyin.dbs.module.content.entity.BasicContent;
import com.zhiyin.dbs.module.content.entity.ContentGroup;
import com.zhiyin.file.ServerUploadFile;
import com.zhiyin.file.qiniu.FileBucket;
import com.zhiyin.file.qiniu.UploadFileRet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 添加内容，会按照目录将旧的音频删除
 * Created by hg on 2016/6/7.
 */
@Slf4j
public class ContentAdd {

    static boolean test = false;
    static int mp3num = 0;


//    重要
    static Long roleId = 4L;

    public static void main(String[] args) throws IOException {

        String a="love23next234csdn3423javaeye";
        log.info("" + getDigit(a));

        String baseDir = DataConfig.AddrContentPath;
        File baseFile = new File(baseDir);
        File[] files = baseFile.listFiles();

        boolean succ = true;
        for (File f : files) {

            log.info("文件夹:{}",f.getName());

            boolean isSubAddr = false;
            File[] subFile = f.listFiles();
            for (File tmp : subFile) {
                if (tmp.isDirectory()) {
                    boolean ret = insertOne(f.getName(),tmp);
                    if (ret == false) {
                        System.exit(-1);
                    }
                    isSubAddr = true;
                }
            }
            if (!isSubAddr) {
                boolean ret = insertOne(f.getName(),f);
                if (ret == false) {
                    succ = false;
                    System.exit(-1);
                }
            }
        }

        System.out.println("content add succ end!" +mp3num);

    }

    public static boolean insertOne(String parAddrName,File conFolder) throws IOException {

        String[] suf = {"mp3"};
        String[] txt = {"txt"};
        Map<String, String> filenameContentTitleMap = Maps.newHashMap();
        Collection<File> mp3s = FileUtils.listFiles(conFolder, suf, false);

        // 由于 文件标题文件中文件名 与 真实文件名 不一致
        Map<Integer,File> fileIndexFileMap = Maps.newConcurrentMap();
        for (File t : mp3s) {
            if (t.getName().startsWith(".")) {
                log.error("file {} error.", t.getPath());
                continue;
            }
            Integer index = getDigit(t.getName().split("\\.")[0]);
            if(index > 30){
                System.out.println("一般情况一个热点不可能有30个音频。");
                System.exit(-1);
            }
            fileIndexFileMap.put(index,t);
        }
        List<File> mp3List = Lists.newArrayList();
        for(int i = 1; i <= fileIndexFileMap.size(); i++){
            mp3List.add(fileIndexFileMap.get(i));
        }
//        log.info("{} mp3 {}", conFolder.getName(), mp3List.size());
        Collection<File> txts = FileUtils.listFiles(conFolder, txt, false);
        List<File> txtList = Lists.newArrayList();
        for (File t : txts) {
            if (t.getName().startsWith(".")) {
                continue;
            }
            txtList.add(t);
        }

        if (txtList == null || txtList.size() == 0) {
            log.error("音频文件-标题对照表为空.{} {}", conFolder.getName(), JSON.toJSONString(txtList));
            return false;
        }

        List<String> txtLines = FileUtils.readLines(txtList.get(0), "GBK");
        List<String> titleList = Lists.newArrayList();
        for (String str : txtLines) {
            if (Strings.isNullOrEmpty(str.trim())) {
                continue;
            }
            String[] info = str.split("\\s+", 2);
//            log.info(str);
//            String index = info[0].trim().split("\\.")[0].split("_")[1];
//            log.info("index " +index);
//            filenameContentTitleMap.put(index, info[1].trim());
            titleList.add(info[1].trim());
        }

        if (mp3List.size() != titleList.size()) {
            log.error("mp3 num not equal title num.");
            return false;
        }

        // 添加内容组
        ContentGroup cg = new ContentGroup();
        cg.setTitle(parAddrName+"-" +conFolder.getName());
        cg.setRoleId(roleId);

        Long cgId = 1L;
        if (!test) {
            // 先删除后添加
            List<ContentGroup> insertList = ContentUtil.selCgList(parAddrName,conFolder.getName());
            for (ContentGroup tmp : insertList) {
                DubboServiceFactory.contentGroupService().deleteRealByPrimaryKey(tmp.getId());
            }
            cgId = DubboServiceFactory.contentGroupService().insertSelectiveGet(cg);
        }
        if (cgId == null || cgId < 0) {
            log.error("add cg id should >0 .");
            return false;
        }
        for (int i = 0; i < mp3List.size(); i++) {
            File mp3 = mp3List.get(i);
            mp3num ++;
            UploadFileRet uploadRet = new UploadFileRet();
            if (test) {
                uploadRet.setName(mp3.getName());
            } else {
                uploadRet = ServerUploadFile.upload(FileBucket.ContentAudio.getValue(), mp3List.get(i));
            }
            if (Strings.isNullOrEmpty(uploadRet.getName())) {
                log.error("upload file name should not be null.{}", mp3.getName());
                return false;
            }
            long time = Mp3TimeUtil.getTime(mp3List.get(i));
            if (time <= 0) {
                log.error("time should >0 ,{}", mp3.getName());
                return false;
            }

            BasicContent bc = new BasicContent();

//            String index = mp3.getName().split("\\.")[0].split("_")[1];
//            log.info("mp3 file index:{}",index);
            Integer index = getDigit(mp3.getName().split("\\.")[0]);
            if(index > 30){
                log.error("index too big,{}",mp3.getName());
                System.exit(-1);
            }

            bc.setTitle( titleList.get(i));
//            if (filenameContentTitleMap.containsKey( index )) {
//                bc.setTitle(filenameContentTitleMap.get( index));
//            } else {
//                log.error(" file name title not map,{}", mp3.getPath());
//                return false;
//            }
            bc.setArticleId(cgId);
            bc.setSavePath(uploadRet.getName());
            bc.setDuration((float) (time * 1.0));

            log.info("add content:{} time:{} path:{}", bc.getTitle(), bc.getDuration(), bc.getSavePath());
            if (!test) {
                DubboServiceFactory.basicContentService().insertSelectiveGet(bc);
            }
        }

        return true;
    }


    public static Integer
    getDigit(String text) {

        List<Long>
                digitList = new
                ArrayList<Long>();
        Pattern p =
                Pattern.compile("(\\d+)");


        int ret = 0;
        Matcher m = p.matcher(text);
        while (m.find()) {

            String find =
                    m.group(1).toString();

//            digitList.add(Long.valueOf(find));

            ret = Integer.valueOf(find);

        }
        return ret;
    }



}
