package com.zhiyin.manager.module.address.vo;

import com.zhiyin.app.api.common.module.S2cObj;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by hg on 2016/5/2.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PointVo extends S2cObj {
    private Double lon;

    private Double lat;

}
