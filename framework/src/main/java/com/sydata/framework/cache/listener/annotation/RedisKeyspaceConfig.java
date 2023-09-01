package com.sydata.framework.cache.listener.annotation;


import java.lang.annotation.*;

/**
 * @author lzq
 * @describe redis key空间事件监听配置
 * @date 2022-04-10 14:50
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedisKeyspaceConfig {

    /**
     * key前缀：监听时会在此key之前拼上applicationName:（目的为了系统隔离）
     */
    String keyPrefix() default "";
}
