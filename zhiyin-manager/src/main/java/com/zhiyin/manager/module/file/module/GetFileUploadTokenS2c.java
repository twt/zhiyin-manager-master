package com.zhiyin.manager.module.file.module;


import com.zhiyin.manager.module.common.module.S2cObj;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author hg
 */
@Getter
@Setter
public class GetFileUploadTokenS2c extends S2cObj {

    private String name;
    private String originName;
    private String token;
    private String key;
    private String domain;
    private String prefix;
    private String bucket;

}
