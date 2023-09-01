package com.sydata.framework.queue;

import cn.hutool.core.map.MapUtil;
import com.sydata.framework.queue.annotation.RedisQueueListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import static java.lang.Boolean.TRUE;
import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

/**
 * @author lzq
 * @describe redis队列消费者注册
 * @date 2022-07-18 15:07
 */
@ConditionalOnProperty(name = "xxl-job.enabled", matchIfMissing = true)
@Component
@Slf4j
public class RedisQueueRegister implements InstantiationAwareBeanPostProcessor, CommandLineRunner {

    private Map<String, List<RedisQueueInvoker>> redisQueueMap = MapUtil.newHashMap();

    @Resource
    private Executor taskExecutor;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            RedisQueueListener listener = findAnnotation(method, RedisQueueListener.class);
            if (listener == null) {
                continue;
            }
            method.setAccessible(TRUE);

            // 注册
            String queueName = RedisQueueProduce.buildQueueName(listener.queueName());
            RedisQueueInvoker redisQueueInvoker = new RedisQueueInvoker(bean, method);
            redisQueueMap.computeIfAbsent(queueName, (q) -> new ArrayList<>()).add(redisQueueInvoker);

        }
        return bean;
    }

    @Override
    public void run(String... args) {
        redisQueueMap.forEach((queueName, consumes) -> {
            RedisQueueConsume redisQueueConsume = new RedisQueueConsume(queueName, consumes, taskExecutor);
            Thread thread = new Thread(redisQueueConsume, "redisQueueConsumeThread-" + queueName);
            thread.setDaemon(TRUE);
            thread.start();
        });
    }
}
