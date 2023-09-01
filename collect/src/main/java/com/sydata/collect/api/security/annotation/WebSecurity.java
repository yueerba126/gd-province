package com.sydata.collect.api.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lzq
 * @describe WEB安全标识注解
 * @date 2022-04-07 11:06
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WebSecurity {

    /**
     * 请求解密
     */
    boolean decrypt() default true;

    /**
     * 响应加密
     */
    boolean encrypt() default false;

    /**
     * 请求重放
     */
    boolean requestReplay() default true;
}
