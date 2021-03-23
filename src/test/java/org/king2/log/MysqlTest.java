package org.king2.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.king2.log.entity.AccessLog;
import org.king2.log.mapper.AccessLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 描述:数据库测试
 * @author 刘梓江
 * @Date 2021/3/15 11:26
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MysqlTest {

    @Autowired
    private AccessLogMapper accessLogMapper;

    @Test
    public void add(){
        AccessLog accessLog=new AccessLog();
        accessLog.setId(134234553535414L);
        accessLogMapper.insertSelective(accessLog);
    }
}
