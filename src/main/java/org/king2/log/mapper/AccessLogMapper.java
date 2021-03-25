package org.king2.log.mapper;

import org.king2.log.entity.AccessLog;

public interface AccessLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AccessLog record);

    int insertSelective(AccessLog record);

    AccessLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccessLog record);

    int updateByPrimaryKeyWithBLOBs(AccessLog record);

    int updateByPrimaryKey(AccessLog record);




}