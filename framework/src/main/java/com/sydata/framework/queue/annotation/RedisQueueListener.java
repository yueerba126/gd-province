package com.sydata.framework.queue.annotation;

import java.lang.annotation.*;

/**
 * @author lzq
 * @describe redis 队列监听
 * @date 2022-07-18 14:43
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedisQueueListener {

    /**
     * 队列名称
     */
    String queueName();
}
