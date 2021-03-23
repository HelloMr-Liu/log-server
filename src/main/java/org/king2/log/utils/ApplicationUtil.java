package org.king2.log.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 描述:一些小工具方法
 * @author 刘梓江
 * @Date 2021/3/19 20:13
 */
public class ApplicationUtil {

    /**
     * 打印获取日志信息
     * @param t
     * @return
     */
    public static String getExceptionMessage(Throwable t) {
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }
}
