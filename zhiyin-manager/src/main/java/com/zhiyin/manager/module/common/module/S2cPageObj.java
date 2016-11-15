package com.zhiyin.manager.module.common.module;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class S2cPageObj extends S2cObj{

    private Integer pageSize = 20;

    //当前页的数量
    private int pageNum;



    //总记录数
    private long totalNum;
    //总页数
    private int totalPage;


}