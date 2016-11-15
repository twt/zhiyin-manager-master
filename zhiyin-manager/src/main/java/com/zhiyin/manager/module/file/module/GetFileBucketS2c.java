package com.zhiyin.manager.module.file.module;


import com.zhiyin.manager.module.common.module.S2cObj;
import com.zhiyin.manager.module.file.vo.FileBucketVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by hg on 2016/5/2.
 */
@Getter
@Setter
public class GetFileBucketS2c extends S2cObj {
    private List<FileBucketVo> list;
}
