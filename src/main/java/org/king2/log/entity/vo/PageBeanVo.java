package org.king2.log.entity.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：封装分页对象
 * 作者：刘梓江
 * 时间：2020/9/10  10:30
 */
public class PageBeanVo<T>  {

    /**
     * 数据集合
     */
    private List<T> list=new ArrayList<>();

    /**
     * 当前页
     */
    private Integer currentPage=0;

    /**
     * 每页多少数据
     */
    private Integer pageSize=0;

    /**
     * 数据总条数
     */
    private Long totalCount=0L;

    /**
     * 总页数
     */
    private Integer pageCount=0;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }
}
    
    
    