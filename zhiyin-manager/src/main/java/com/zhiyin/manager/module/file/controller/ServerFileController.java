package com.zhiyin.manager.module.file.controller;

import java.io.*;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.zhiyin.audio.Mp3TimeUtil;
import com.zhiyin.file.ServerUploadFile;
import com.zhiyin.file.qiniu.UploadFileRet;
import com.zhiyin.manager.module.common.controller.BaseController;
import com.zhiyin.manager.module.common.module.S2cObj;
import com.zhiyin.manager.module.file.module.ServerUploadS2c;
import com.zhiyin.manager.web.entity.WebResp;
import lombok.extern.slf4j.Slf4j;


import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.util.WebUtils;



/**
 * 图片上传controller
 * */
@Slf4j
@RestController
@RequestMapping(value = "/files")
public class ServerFileController extends BaseController{

	/**
	 * 上传文件
	 */
    @ResponseBody
    @RequestMapping(value = "/upload/local", method = { RequestMethod.POST })
    public ResponseEntity<?> saveLocal(@RequestParam("file") MultipartFile uploadfile,HttpServletRequest request,HttpServletResponse response){
        if(uploadfile.isEmpty()){
            log.error("upload file is null.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String fileType = WebUtils.findParameterValue(request, "file_type");
        try {
            log.info("origin upload file: {}",uploadfile.getOriginalFilename());

            String filename = uploadfile.getOriginalFilename();
            String directory = request.getSession().getServletContext().getRealPath("/") + "";
            String filepath = Paths.get(directory, filename).toString();

            log.info("new file path: {}",filepath);
            // Save the file locally
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(uploadfile.getBytes());
            stream.close();

        }catch (Exception e) {
           log.info("upload file error,",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
	}

    /**
     * 上传文件
     */
    @ResponseBody
    @RequestMapping(value = "/upload/qiniu", method = { RequestMethod.POST })
    public WebResp<S2cObj> saveServer(@RequestParam("file") MultipartFile uploadfile, HttpServletRequest request ){
        if(uploadfile.isEmpty()){
            log.error("upload file is null.");
            return failReqRet();
        }

        String fileType = WebUtils.findParameterValue(request, "file_type");
        try {
            log.info("origin upload file: {}",uploadfile.getOriginalFilename());

            String filename = uploadfile.getOriginalFilename();
            String directory = request.getSession().getServletContext().getRealPath("/") + "upload/";
            FileUtils.forceMkdir(new File(directory));
            String filepath = Paths.get(directory, filename).toString();

            log.info("new file path: {}",filepath);
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(uploadfile.getBytes());
            stream.close();

            UploadFileRet uploadRet = ServerUploadFile.upload(fileType, new File(filepath));

            // 获取音频文件的时长
            long time = 0;
            if(filepath.endsWith("mp3")){
                time = Mp3TimeUtil.getTime(filepath);
            }

            ServerUploadS2c s2c = new ServerUploadS2c();
            s2c.setTime(Double.valueOf(time));
            s2c.setName( uploadRet.getName() );

            return succRet(s2c);
        }catch (Exception e) {
            log.info("upload file error,",e);
            return failReqRet();
        }

    }

}
