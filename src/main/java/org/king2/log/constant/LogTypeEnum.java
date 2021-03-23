package org.king2.log.constant;

/**
 * 描述: 定义日志类型信息属性
 * @author 刘梓江
 * @Date 2021/3/19 20:26
 */
public enum LogTypeEnum {

    LOGIN(1,"登录日志"),
    OPERATION(2,"操作日志"),
    EXCEPTION(3,"异常日志");

    public final int code;
    public final String msg;

    private LogTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    //获取对应的实例
    public static LogTypeEnum getInstance(Integer content){
        for(LogTypeEnum instance: LogTypeEnum.values()){
            if(instance.code==content){
                return instance;
            }
        }
        return null;
    }

}
