package com.zhiyin.manager.module.address.vo;


import com.zhiyin.manager.module.common.module.S2cObj;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hg on 2016/5/2.
 */
@Getter
@Setter
public class AddressHotpicVo extends S2cObj {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long addrId;

    private String picUrl;
}