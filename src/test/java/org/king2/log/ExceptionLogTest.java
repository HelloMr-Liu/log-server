package org.king2.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.king2.log.utils.AddressUtils;
import org.king2.log.utils.ApplicationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;


/**
 * 描述: ES客户端测试
 *
 * @author 刘梓江
 * @Date 2021/3/19 14:15
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ExceptionLogTest {

    private final Logger logger= LoggerFactory.getLogger(ExceptionLogTest.class);

    @Autowired
    private HttpServletRequest request;

    @Test
    public void  logPrint() throws  Exception{

    }
}
