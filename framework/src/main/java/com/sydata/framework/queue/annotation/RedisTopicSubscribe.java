package com.sydata.framework.queue.annotation;

import java.lang.annotation.*;

/**
 * @author lzq
 * @describe redis topic订阅
 * @date 2022-07-18 14:43
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedisTopicSubscribe {

    /**
     * topic名称
     */
    String topicName();
}
