package com.sydata.framework.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.lang.reflect.Method;

/**
 * @author lzq
 * @describe redis队列执行器
 * @date 2022-07-18 15:15
 */
@Data
@AllArgsConstructor
public class RedisQueueInvoker {

    private Object bean;

    private Method method;

    /**
     * 调用消费者
     *
     * @param message 消息
     */
    @SneakyThrows(Throwable.class)
    public void invoke(Object message) {
        method.invoke(bean, message);
    }
}
