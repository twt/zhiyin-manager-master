package com.zhiyin.manager.module.file.vo;

import com.zhiyin.app.api.common.module.S2cObj;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hg on 2016/5/2.
 */
@Getter
@Setter
public class FileBucketVo extends S2cObj {

    private Integer type;
    private String value;
    private String name;

}
