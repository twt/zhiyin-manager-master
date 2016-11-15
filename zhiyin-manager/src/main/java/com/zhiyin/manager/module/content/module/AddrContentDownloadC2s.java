package com.zhiyin.manager.module.content.module;

import com.zhiyin.manager.module.common.module.C2sObj;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 *
 * Created by hg on 2016/1/9.
 */
@Deprecated
@Getter
@Setter
public class AddrContentDownloadC2s extends C2sObj {

    @Min(value = 1, message = "{common.parameter.invalid}")
    private Long addressId;
    private String downloadUrl; // 下载链接，

    @Min(value = 0, message = "{common.parameter.invalid}")
    private Integer isFull;



}
