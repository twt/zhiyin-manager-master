package com.zhiyin.manager.module.user.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zhiyin.manager.module.common.module.S2cObj;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Created by hg on 2016/4/29.
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoVo extends S2cObj{

    private static final long serialVersionUID = 1L;

    private Long id;

    private String guid;

    private Date createTime;
    private Date updateTime;


    private String email;


    private String telephone;


    private String name;




    private Date lastLoginTime;
//    private Date changePasswordTime;

    private String avatar;

    private Integer gender;

    private String thirdId;
    private Integer thirdType;



}
