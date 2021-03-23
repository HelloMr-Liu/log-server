package org.king2.log.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.king2.log.annotation.AccessDelete;
import org.king2.log.annotation.AccessEditor;
import org.king2.log.annotation.AccessShow;
import org.king2.log.constant.LogAccessTypeEnum;
import org.king2.log.constant.LogTypeEnum;
import org.king2.log.schedule.LogServerSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 描述:全局请求面向切面操作
 * @author 刘梓江
 * @Date 2021/3/22 16:06
 */
@Aspect
@Component
public class GlobalAop {


    @Autowired
    private HttpServletRequest request;

    @Around("execution( * org.king2.log.controller.*.* (..))")
    public Object globalRequest(ProceedingJoinPoint pjp) {
        Object proceed=null;
        try{

            //请求接口信息
            proceed=pjp.proceed();

            //获取日志访问类型
            Signature signature = pjp.getSignature();
            MethodSignature methodSignature = (MethodSignature)signature;
            Method targetMethod = methodSignature.getMethod();
            Method realMethod = pjp.getTarget().getClass().getDeclaredMethod(signature.getName(), targetMethod.getParameterTypes());
            Integer accessType= LogAccessTypeEnum.SYSTEM.code;
            if(realMethod.isAnnotationPresent(AccessDelete.class)){
                accessType= LogAccessTypeEnum.DELETE.code;
            }
            if(realMethod.isAnnotationPresent(AccessEditor.class)){
                accessType= LogAccessTypeEnum.EDITOR.code;
            }
            if(realMethod.isAnnotationPresent(AccessShow.class)){
                accessType= LogAccessTypeEnum.FIND.code;
            }

            //记录日志信息
            LogServerSchedule.packAccessLog(new Date(),accessType, LogTypeEnum.OPERATION.code, JSON.toJSONString(proceed));
        }finally{
            return proceed;
        }
    }
}
