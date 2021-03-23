package org.king2.log;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 描述: 日志服务启动类
 * @author 刘梓江
 * @Date 2021/3/15 10:01
 */
@EnableScheduling
@SpringBootApplication
@MapperScan("org.king2.log.mapper")
public class LogServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogServerApplication.class);
    }
}
