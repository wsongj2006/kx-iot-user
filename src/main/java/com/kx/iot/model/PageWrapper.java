package com.kx.iot.model;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class PageWrapper<T> {
    private List<T> dataList;

    private long total;

    private int pages;

    private int pageNo;

    private int pageSize;

    public static PageWrapper build(List dataList, PageInfo pageInfo) {
        PageWrapper pageWrapper = new PageWrapper();
        pageWrapper.setDataList(dataList);
        pageWrapper.setPageNo(pageInfo.getPageNum());
        pageWrapper.setPageSize(pageInfo.getPageSize());
        pageWrapper.setPages(pageInfo.getPages());
        return pageWrapper;
    }
}
