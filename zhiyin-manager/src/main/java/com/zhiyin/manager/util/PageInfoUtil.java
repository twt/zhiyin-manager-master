package com.zhiyin.manager.util;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zhiyin.dbs.module.common.module.ReqPageInfo;
import com.zhiyin.dbs.module.common.module.ResponsePageInfo;
import com.zhiyin.manager.module.common.module.S2cPageObj;
import com.zhiyin.utils.bean.BeanMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hg on 2016/1/9.
 */
public class PageInfoUtil {
    private static final Logger logger = LoggerFactory.getLogger(PageInfoUtil.class);

    public static ReqPageInfo formatPage(ReqPageInfo info) {
        if (info == null) {
            return getDefault();
        }
        if (info.getP() <= 0) {
            logger.warn("req param page num invalid, req info:{}", JSON.toJSONString(info));
            info.setP(1);
        }

        if (info.getS() <= 0) {
            logger.warn("req param page size invalid, req info:{}", JSON.toJSONString(info));
            info.setS(10);
        }
        return info;
    }

    public static ReqPageInfo getDefault() {
        ReqPageInfo info = new ReqPageInfo();
        info.setP(1);
        info.setS(10);
        return info;
    }

    public static ResponsePageInfo format(PageInfo<?> pageInfo) {
        return BeanMapper.map(pageInfo, ResponsePageInfo.class);
    }


    public static S2cPageObj s2c(PageInfo<?> pageInfo) {
        S2cPageObj s2c = new S2cPageObj();
        s2c.setPageNum(pageInfo.getPageNum());
        s2c.setPageSize(pageInfo.getPageSize());
        s2c.setTotalNum(pageInfo.getTotal());
        s2c.setTotalPage(pageInfo.getPages());
        return s2c;
    }
}
