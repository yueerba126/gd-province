package com.sydata.framework.queue;

import cn.hutool.extra.spring.SpringUtil;
import com.sydata.framework.queue.RedisQueueInvoker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @author lzq
 * @describe redis队列消费者
 * @date 2022-07-18 14:54
 */
@Slf4j
@AllArgsConstructor
public class RedisQueueConsume implements Runnable {

    private String queueName;

    private List<RedisQueueInvoker> consumes;

    private Executor taskExecutor;

    @Override
    public void run() {
        // 获取队列和异步线程池
        BlockingQueue<String> blockingQueue = SpringUtil.getBean(RedissonClient.class).getBlockingQueue(queueName);

        while (true) {
            try {
                Object message = blockingQueue.poll(10, TimeUnit.SECONDS);
                if (message == null) {
                    continue;
                }

                taskExecutor.execute(() -> invoke(message));
            } catch (Exception e) {
                log.error("RedisQueueConsume_{}:运行时发生异常", queueName, e);
            }
        }
    }


    /**
     * 通知监听者
     *
     * @param message 消息
     */
    public void invoke(Object message) {
        consumes.forEach(consume -> {
            try {
                consume.invoke(message);
            } catch (Exception e) {
                log.error("RedisQueueConsume_{}:调用消费者时发生异常", queueName, e);
            }
        });
    }
}
