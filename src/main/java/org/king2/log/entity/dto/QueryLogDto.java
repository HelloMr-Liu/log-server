package org.king2.log.entity.dto;

import org.king2.log.valid.QueryValid;

import javax.validation.constraints.NotNull;

/**
 * 描述:接收查询日志信息
 * @author 刘梓江
 * @Date 2021/3/19 15:41
 */
public class QueryLogDto  extends QueryGlobalDto {

    /**
     * 日志类型
     */
    @NotNull(message = "日志类型不能为空",groups = QueryValid.class)
    private Integer accessType;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;


    public Integer getAccessType() {
        return accessType;
    }

    public void setAccessType(Integer accessType) {
        this.accessType = accessType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
