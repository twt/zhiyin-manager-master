package com.zhiyin.manager.module.user.vo;

import com.zhiyin.manager.module.common.module.S2cPageObj;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by hg on 2016/4/29.
 */
@Getter
@Setter
@ToString
public class ListUserInfoS2c extends S2cPageObj {

    private List<UserInfoVo> list;

}
