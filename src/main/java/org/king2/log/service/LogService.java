package org.king2.log.service;

import org.king2.log.entity.dto.QueryLogDto;

import java.io.IOException;

/**
 * 描述: 操作日志信息业务接口
 * @author 刘梓江
 * @Date 2021/3/19 15:43
 */
public interface LogService {

    /**
     * 获取日志类型
     * @param queryLogDto
     * @return
     */
    public Object findLogByCondition(QueryLogDto queryLogDto);

    public Object add();
    public Object update();
    public Object delete();


}
