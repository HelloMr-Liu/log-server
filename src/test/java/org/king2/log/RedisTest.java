package org.king2.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.king2.log.entity.vo.LoginSuccessfulInfoVo;
import org.king2.log.utils.IdUtil;
import org.king2.log.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * 描述:缓存测试
 * @author 刘梓江
 * @Date 2021/3/15 11:25
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void add(){
        String  uuId=UUID.randomUUID().toString();
        System.out.println();
        redisUtil.set(uuId,new LoginSuccessfulInfoVo(IdUtil.nextId(),"刘梓江","15717007490"));
    }
}
