package org.king2.log.constant;

/**
 * 描述: 定义日志访问类型信息属性
 * @author 刘梓江
 * @Date 2021/3/19 20:26
 */
public enum LogAccessTypeEnum {

    SYSTEM(0,"系统"),
    FIND(1,"查看"),
    EDITOR(2,"编辑"),
    DELETE(3,"删除");

    public final int code;
    public final String msg;

    private LogAccessTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    //获取对应的实例
    public static LogAccessTypeEnum getInstance(Integer content){
        for(LogAccessTypeEnum instance: LogAccessTypeEnum.values()){
            if(instance.code==content){
                return instance;
            }
        }
        return null;
    }

}
