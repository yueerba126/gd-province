package com.sydata.framework.cache.support;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.sydata.framework.cache.listener.RedisKeyEventEnum;
import com.sydata.framework.cache.listener.annotation.RedisKeyspaceConfig;
import com.sydata.framework.cache.listener.annotation.RedisKeyspaceEvent;
import com.sydata.framework.cache.properties.CacheConfigProperties;
import com.sydata.framework.queue.RedisQueueProduce;
import com.sydata.framework.queue.annotation.RedisQueueListener;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

import static com.sydata.framework.cache.listener.RedisKeyEventEnum.*;
import static com.sydata.framework.cache.properties.CacheConfigProperties.CACHE_PREFIX;
import static com.sydata.framework.cache.properties.CacheConfigProperties.KEY_SEGMENTATION;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * <p>
 * redis和Caffeine 缓存管理器
 * </p>
 *
 * @author lzq
 * @date 2021/7/27 15:29
 */
@Slf4j
@RedisKeyspaceConfig(keyPrefix = CACHE_PREFIX + KEY_SEGMENTATION)
@Component("cacheManager")
public class RedisCaffeineCacheManager implements CacheManager {

    public static final String DELAYED_SYNCHRONIZATION_QUEUE = "framework:cache:delayedSynchronizationQueue";

    private final ConcurrentMap<String, MultiCache> cacheMap = new ConcurrentHashMap<>(128);

    private final CacheConfigProperties cacheConfigProperties;

    private final RedisTemplate<String, Object> redisTemplate;

    private final com.github.benmanes.caffeine.cache.Cache<String, Object> caffeineCache;

    private final RedissonClient redisson;

    private final RedisQueueProduce redisQueueProduce;

    public RedisCaffeineCacheManager(CacheConfigProperties cacheConfigProperties,
                                     RedisTemplate<String, Object> redisTemplate, RedissonClient redisson,
                                     RedisQueueProduce redisQueueProduce) {
        super();
        this.cacheConfigProperties = cacheConfigProperties;
        this.redisTemplate = redisTemplate;
        this.caffeineCache = caffeineCache();
        this.redisson = redisson;
        this.redisQueueProduce = redisQueueProduce;
    }

    @Override
    public MultiCache getCache(String name) {
        return cacheMap.computeIfAbsent(name, key -> {
            Consumer<Collection<String>> cacheDelayedSynchronization = this::cacheDelayedSynchronizationQueueProduce;

            return new RedisCaffeineCache(key, cacheConfigProperties, caffeineCache,
                    redisTemplate, redisson, cacheDelayedSynchronization);
        });
    }

    public com.github.benmanes.caffeine.cache.Cache<String, Object> caffeineCache() {
        Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder();

        // 缓存时间驱逐策略
        if (cacheConfigProperties.getCaffeineExpireAfterAccess() > 0) {
            cacheBuilder.expireAfterAccess(cacheConfigProperties.getCaffeineExpireAfterAccess(), SECONDS);
        }
        if (cacheConfigProperties.getCaffeineExpireAfterWrite() > 0) {
            cacheBuilder.expireAfterWrite(cacheConfigProperties.getCaffeineExpireAfterWrite(), SECONDS);
        }

        // 缓存容量驱逐策略
        if (cacheConfigProperties.getCaffeineInitialCapacity() > 0) {
            cacheBuilder.initialCapacity(cacheConfigProperties.getCaffeineInitialCapacity());
        }
        if (cacheConfigProperties.getCaffeineMaximumSize() > 0) {
            cacheBuilder.maximumSize(cacheConfigProperties.getCaffeineMaximumSize());
        }

        return cacheBuilder.build();
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheMap.keySet();
    }


    @RedisKeyspaceEvent(event = {SET, EXPIRED, DEL, RENAME_TO, RENAME_FROM})
    private void handleMessage(String redisKey, RedisKeyEventEnum eventEnum) {
        log.trace("redis键空间事件={},移除本地缓存={}", eventEnum, redisKey);
        caffeineCache.invalidate(redisKey);
    }

    /**
     * 缓存延时同步队列生产
     *
     * @param redisKeys redis缓存键集合
     */
    private void cacheDelayedSynchronizationQueueProduce(Collection<String> redisKeys) {
        long delay = cacheConfigProperties.getCacheAsyExpelDelay();
        for (String redisKey : redisKeys) {
            redisQueueProduce.delayedOffer(DELAYED_SYNCHRONIZATION_QUEUE, redisKey, delay, SECONDS);
        }
    }

    /**
     * 缓存延时同步队列消费处理
     *
     * @param redisKey redis缓存键
     */
    @RedisQueueListener(queueName = DELAYED_SYNCHRONIZATION_QUEUE)
    private void cacheDelayedSynchronizationQueueConsume(String redisKey) {
        redisTemplate.delete(redisKey);
    }
}
