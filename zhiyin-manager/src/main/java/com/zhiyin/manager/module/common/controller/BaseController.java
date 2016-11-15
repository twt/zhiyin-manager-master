package com.zhiyin.manager.module.common.controller;

/**
 * Created by hg on 2016/3/19.
 */

import com.alibaba.fastjson.JSON;
import com.zhiyin.dbs.module.oauth.common.OauthUserDetails;
import com.zhiyin.dbs.module.user.entity.UserInfo;
import com.zhiyin.dbs.module.user.service.IUserInfoService;
import com.zhiyin.manager.module.common.module.AddSuccS2c;
import com.zhiyin.manager.module.common.module.S2cObj;
import com.zhiyin.manager.web.entity.WebResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Controller;



@Controller
public class BaseController {

    public static WebResp<S2cObj> notFound(){
        return new WebResp<>(
                HttpStatus.NOT_FOUND.value(), "" , null);
    }


    /**
     * 失败返回值
     * @param failInfo
     * @return
     */
    public static WebResp<S2cObj> failRet(String failInfo){
        return new WebResp<>(
                HttpStatus.BAD_REQUEST.value(), failInfo , null);
    }

//	public static ApiResp failRet(String failInfo){
//		return null;
//	}

    public static WebResp<S2cObj> failReqRet( ){
        return new WebResp<>(
                HttpStatus.BAD_REQUEST.value(), "请求参数错误" , null);
    }

    public static WebResp<S2cObj> failRet(int code,String failInfo){
        return new WebResp<>(
                code, failInfo , null);
    }

    /**
     * 验证失败
     *
     * @return
     */
    public static WebResp<S2cObj> failAuthRet(){
        return new WebResp<>(
                HttpStatus.UNAUTHORIZED.value(), "用户信息无效" , null);
    }

    /**
     * 成功返回值
     * @param succInfo
     * @return
     */
    public static WebResp<S2cObj> succRet(S2cObj succInfo){
        return new WebResp<>(
                HttpStatus.OK.value(),"success" , succInfo);
    }

    public static WebResp<S2cObj> succAddRet( Long id){

        AddSuccS2c s2c = new AddSuccS2c(id);


        return succRet(s2c);
    }
//	public String processSubjectId(){
//
//	}

}
