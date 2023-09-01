package com.sydata.framework.queue;

import com.sydata.framework.queue.annotation.RedisTopicSubscribe;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

import static java.lang.Boolean.TRUE;
import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

/**
 * @author lzq
 * @describe redis Topic订阅者注册
 * @date 2022-07-18 15:07
 */
@Component
@Slf4j
public class RedisTopicRegister implements InstantiationAwareBeanPostProcessor {

    @Resource
    private RedissonClient redisson;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            RedisTopicSubscribe listener = findAnnotation(method, RedisTopicSubscribe.class);
            if (listener == null) {
                continue;
            }
            method.setAccessible(TRUE);

            // 订阅
            String topicName = RedisQueueProduce.buildQueueName(listener.topicName());
            RedisQueueInvoker redisQueueInvoker = new RedisQueueInvoker(bean, method);
            redisson.getTopic(topicName).addListener(Object.class, (s, message) -> redisQueueInvoker.invoke(message));
        }
        return bean;
    }
}
