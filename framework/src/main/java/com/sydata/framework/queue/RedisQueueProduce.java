package com.sydata.framework.queue;

import cn.hutool.extra.spring.SpringUtil;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COLON;

/**
 * @author lzq
 * @describe redis队列生产者
 * @date 2022-07-18 14:42
 */
@Component
public class RedisQueueProduce {

    @Resource
    private RedissonClient redisson;

    /**
     * 发送消息
     *
     * @param queueName 队列名称
     * @param t         消息
     */
    public <T> void offer(String queueName, T t) {
        redisson.getBlockingQueue(buildQueueName(queueName)).offer(t);
    }

    /**
     * 发送延时消息
     *
     * @param queueName 队列名称
     * @param t         消息
     * @param delay     延时时间
     * @param timeUnit  单位
     */
    public <T> void delayedOffer(String queueName, T t, long delay, TimeUnit timeUnit) {
        RBlockingQueue<T> blockingFairQueue = redisson.getBlockingQueue(buildQueueName(queueName));
        redisson.getDelayedQueue(blockingFairQueue).offer(t, delay, timeUnit);
    }

    /**
     * 发布topic消息
     *
     * @param topic topic名称
     * @param t     消息内容
     */
    public <T> void push(String topic, T t) {
        redisson.getTopic(buildQueueName(topic)).publishAsync(t);
    }

    /**
     * 获取队列名称
     *
     * @param queueName 原队列名称
     * @return 存储的队列名称
     */
    public static String buildQueueName(String queueName) {
        return SpringUtil.getApplicationName() + COLON + queueName;
    }
}
