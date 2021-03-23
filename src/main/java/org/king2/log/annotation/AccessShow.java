package org.king2.log.annotation;

import java.lang.annotation.*;

/**
 * 描述: 定义访问查看注解
 * @author 刘梓江
 * @Date 2021/2/25 16:34
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value= ElementType.METHOD)
public @interface AccessShow {
}
