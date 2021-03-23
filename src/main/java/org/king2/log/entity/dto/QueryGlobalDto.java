package org.king2.log.entity.dto;

/**
 * 描述:接收查询日志信息
 * @author 刘梓江
 * @Date 2021/3/19 15:41
 */
public class QueryGlobalDto {


    /**
     * 搜索内容
     */
    private String searchContent;

    /**
     * 当前页
     */
    private Integer currentPage=1;

    /**
     * 每页显示的条数
     */
    private Integer pageSize=10;


    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
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
}
