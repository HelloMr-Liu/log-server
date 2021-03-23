package org.king2.log;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 描述:
 *
 * @author 刘梓江
 * @Date 2021/3/22 16:59
 */
public class Test {


    public static void main(String[] args) {
        String str="";
        try {
            str=test1();
        }catch (Exception e){
            System.out.println("异常");
        }
        System.out.println(str);
    }
    public static String test1()throws Exception{
        return "你好";
    }
}
