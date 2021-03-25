package org.king2.log.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.king2.log.entity.AccessLog;

import java.util.List;

/**
 * 描述:操作访问日志信息表扩展接口
 * @author 刘梓江
 * @Date 2021/3/25 13:46
 */
public interface AccessLogExtendMapper extends  AccessLogMapper {

    /**
     * 批量插入
     * @param accessLogs
     */
    @Insert
    ({
        "<script>",
            " insert into ACCESS_LOG (ID, ACCESS_IP,ACCESS_TYPE,ACCESS_PATH,ACCESS_PARAMETER,ACCESS_AREA,ACCESS_USER_ID , ACCESS_DATE ,ACCESS_RESULT,ACCESS_LOG_TYPE )" ,
            " values ",
            " <foreach collection='contents' item='item' index='index' separator=','>",
            " (" ,
            "  #{item.id}, #{item.accessIp}, #{item.accessType}, #{item.accessPath} ," ,
            "  #{item.accessParameter}, #{item.accessArea}, #{item.accessUserId}, #{item.accessDate}, #{item.accessResult} , #{item.accessLogType} " ,
            " )",
            "</foreach>",
        "</script>"
    })
    public void batchInsert(@Param("contents") List<AccessLog> accessLogs);
}
