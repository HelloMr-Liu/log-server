package org.king2.log.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * ACCESS_LOG 信息表
 */
public class AccessLog {
    /**
     * 访问记录ID
     */
    private Long id;

    /**
     * 访问人IP
     */
    private String accessIp;

    /**
     * 访问类型 查看 增加  修改 删除
     */
    private Integer accessType;

    /**
     * 访问地址
     */
    private String accessPath;

    /**
     * 访问参数
     */
    private String accessParameter;

    /**
     * 访问区域
     */
    private String accessArea;

    /**
     * 访问人ID
     */
    private Long accessUserId;

    /**
     * 访问时间
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date accessDate;

    /**
     * 访问日志类型 一般有登录,操作日志,异常
     */
    private Integer accessLogType;

    /**
     * 访问结果
     */
    private String accessResult;


    /**
     * 判断是否已经插入 1：插入到数据库  0：未插入到ES
     */
    private Integer isInsert=0;

    public Integer getIsInsert() {
        return isInsert;
    }

    public void setIsInsert(Integer isInsert) {
        this.isInsert = isInsert;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessIp() {
        return accessIp;
    }

    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp == null ? null : accessIp.trim();
    }

    public Integer getAccessType() {
        return accessType;
    }

    public void setAccessType(Integer accessType) {
        this.accessType = accessType;
    }

    public String getAccessPath() {
        return accessPath;
    }

    public void setAccessPath(String accessPath) {
        this.accessPath = accessPath == null ? null : accessPath.trim();
    }

    public String getAccessParameter() {
        return accessParameter;
    }

    public void setAccessParameter(String accessParameter) {
        this.accessParameter = accessParameter == null ? null : accessParameter.trim();
    }

    public String getAccessArea() {
        return accessArea;
    }

    public void setAccessArea(String accessArea) {
        this.accessArea = accessArea == null ? null : accessArea.trim();
    }

    public Long getAccessUserId() {
        return accessUserId;
    }

    public void setAccessUserId(Long accessUserId) {
        this.accessUserId = accessUserId;
    }

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }

    public Integer getAccessLogType() {
        return accessLogType;
    }

    public void setAccessLogType(Integer accessLogType) {
        this.accessLogType = accessLogType;
    }

    public String getAccessResult() {
        return accessResult;
    }

    public void setAccessResult(String accessResult) {
        this.accessResult = accessResult == null ? null : accessResult.trim();
    }


    public AccessLog(){


    }

    public AccessLog(Long id, String accessIp, Integer accessType, String accessPath, String accessParameter, String accessArea, Long accessUserId, Date accessDate, Integer accessLogType, String accessResult) {
        this.id = id;
        this.accessIp = accessIp;
        this.accessType = accessType;
        this.accessPath = accessPath;
        this.accessParameter = accessParameter;
        this.accessArea = accessArea;
        this.accessUserId = accessUserId;
        this.accessDate = accessDate;
        this.accessLogType = accessLogType;
        this.accessResult = accessResult;
    }
}