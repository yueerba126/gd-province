package com.sydata.framework.cache.listener.annotation;

import com.sydata.framework.cache.listener.RedisKeyEventEnum;

import java.lang.annotation.*;

/**
 * @author lzq
 * @describe redis key空间事件监听器，该注解修饰的方法：param[0]=string(缓存key必填)，param[1]=RedisKeyEventEnum(事件选填)
 * @date 2022-04-10 14:50
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisKeyspaceEvent {

    /**
     * key前缀：监听时会在此key之前拼上applicationName:（目的为了系统隔离），必填项如不填则拿RedisKeyspaceConfig中的keyPrefix
     *
     * @see RedisKeyspaceConfig
     */
    String keyPrefix() default "";

    /**
     * 监听的事件
     */
    RedisKeyEventEnum[] event() default {};

    /**
     * 回调时的key是否需要包含前缀
     * <p>
     * 完整的key=applicationName:keyPrefix+key
     * 不包含前缀的key实际上就是截取applicationName:keyPrefix
     */
    boolean keyIncludePrefix() default true;
}
