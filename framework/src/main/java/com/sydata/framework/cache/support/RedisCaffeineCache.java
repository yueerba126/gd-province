package com.sydata.framework.cache.support;

import cn.hutool.extra.spring.SpringUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.sydata.framework.cache.properties.CacheConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import static com.sydata.framework.cache.properties.CacheConfigProperties.CACHE_PREFIX;
import static com.sydata.framework.cache.properties.CacheConfigProperties.KEY_SEGMENTATION;
import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * <p>
 * redis和Caffeine 缓存处理器
 * </p>
 *
 * @author lzq
 * @date 2021/7/27 15:29
 */
@Slf4j
public class RedisCaffeineCache extends AbstractValueAdaptingCache implements MultiCache {

    private final String cacheName;

    private final RedisTemplate<String, Object> redisTemplate;

    private final Cache<String, Object> caffeineCache;

    private final CacheConfigProperties cacheConfigProperties;

    private final RedissonClient redisson;

    private final String cacheNamePrefix;

    private final Consumer<Collection<String>> cacheDelayedSynchronization;

    public RedisCaffeineCache(String cacheName, CacheConfigProperties cacheConfigProperties,
                              Cache<String, Object> caffeineCache,
                              RedisTemplate<String, Object> redisTemplate,
                              RedissonClient redisson,
                              Consumer<Collection<String>> cacheDelayedSynchronization) {
        super(cacheConfigProperties.isCacheNullValues());
        this.cacheName = cacheName;
        this.cacheConfigProperties = cacheConfigProperties;
        this.caffeineCache = caffeineCache;
        this.redisTemplate = redisTemplate;
        this.redisson = redisson;
        this.cacheDelayedSynchronization = cacheDelayedSynchronization;

        this.cacheNamePrefix = new StringJoiner(KEY_SEGMENTATION)
                .add(SpringUtil.getApplicationName())
                .add(CACHE_PREFIX)
                .add(cacheName) + KEY_SEGMENTATION;

        log.debug("创建缓存实例名:{},缓存key前缀:{}", cacheName, cacheNamePrefix);
    }

    @Override
    public String getName() {
        return this.cacheName;
    }

    @Override
    public Object getNativeCache() {
        return this;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        // 双端检锁(防止缓存击穿)
        String cacheKey = this.cacheKey(key);
        Object value = this.loopCache(cacheKey);
        if (value != null) {
            return (T) super.fromStoreValue(value);
        }

        // 根据key获取加载key的锁
        RLock lock = this.getLock(cacheKey);
        try {
            lock.lock();
            // 再次从缓存中获取值，有则直接返回无则加锁执行目标方法
            value = this.loopCache(cacheKey);
            if (value != null) {
                return (T) super.fromStoreValue(value);
            }

            // 执行目标方法
            value = valueLoader.call();

            // 将结果写入缓存
            this.put(key, value);
            return (T) value;
        } catch (Exception e) {
            throw new ValueRetrievalException(cacheKey, valueLoader, e.getCause());
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Override
    public void put(Object key, Object value) {
        String cacheKey = this.cacheKey(key);
        redisTemplate.opsForValue().set(cacheKey, value, this.getExpire(), SECONDS);
        caffeineCache.put(cacheKey, super.toStoreValue(value));
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        String cacheKey = this.cacheKey(key);

        // 先设置redis，如果成功便设置本地缓存
        Boolean state = redisTemplate.opsForValue().setIfPresent(cacheKey, value, this.getExpire(), SECONDS);
        if (state != null && state) {
            caffeineCache.put(cacheKey, super.toStoreValue(value));
        }
        return super.toValueWrapper(state ? value : this.loopCache(cacheKey));
    }

    @Override
    public void evict(Object key) {
        String cacheKey = this.cacheKey(key);
        // 先清除redis中缓存数据，然后清除caffeine中的缓存，避免短时间内其他请求会再从redis里加载到caffeine中
        redisTemplate.delete(cacheKey);
        caffeineCache.invalidate(cacheKey);

        cacheDelayedSynchronization.accept(Collections.singleton(cacheKey));
    }

    @Override
    public void clear() {
        // 先清除redis中缓存数据，然后清除caffeine中的缓存，避免短时间内其他请求会再从redis里加载到caffeine中
        String pattern = cacheNamePrefix + "*";
        Set<String> keys = redisTemplate.keys(pattern);
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        redisTemplate.delete(keys);
        caffeineCache.invalidateAll(keys);

        cacheDelayedSynchronization.accept(keys);
    }

    @Override
    protected Object lookup(Object key) {
        return this.loopCache(this.cacheKey(key));
    }

    @Override
    public <V> Map<String, V> batchGet(List<String> cacheKeys) {
        if (CollectionUtils.isEmpty(cacheKeys)) {
            return Collections.emptyMap();
        }

        long start = currentTimeMillis();

        // 加载一级缓存
        List<String> copyKeys = new ArrayList<>(cacheKeys);
        Map<String, Object> valuesMap = new HashMap<>(caffeineCache.getAllPresent(copyKeys));
        int caffeineCacheSize = valuesMap.size();

        if (caffeineCacheSize != copyKeys.size()) {
            // 删除掉加载到一级缓存的key，剩下的去redis加载
            copyKeys.removeAll(valuesMap.keySet());
            List<Object> caches = redisTemplate.opsForValue().multiGet(copyKeys);

            // 查询到的缓存列表与copyKeys列表下标相等，将查询到的值加入到返回列表和一级缓存
            for (int i = 0; i < copyKeys.size(); i++) {
                String cacheKey = copyKeys.get(i);
                Object value = caches.get(i);
                if (Objects.nonNull(value)) {
                    valuesMap.put(cacheKey, value);
                    caffeineCache.put(cacheKey, value);
                }
            }
        }
        log.trace("批量获取缓存获取size={}，caffeineCache(一级缓存)命中size={},redisCache(二级缓存)命中size={},总耗时={}毫秒",
                cacheKeys.size(), caffeineCacheSize, valuesMap.size() - caffeineCacheSize, currentTimeMillis() - start);
        copyKeys.clear();
        return (Map<String, V>) valuesMap;
    }

    @Override
    public <V> void batchPut(Map<String, V> cacheMap) {
        if (MapUtils.isNotEmpty(cacheMap)) {
            // 加入一级缓存和redis缓存设置过期时间
            caffeineCache.putAll(cacheMap);

            RedisSerializer keySerializer = redisTemplate.getKeySerializer();
            RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
            long seconds = this.getExpire();
            redisTemplate.executePipelined((RedisCallback) connection -> {
                cacheMap.forEach((k, v) -> {
                    byte[] rawKey = keySerializer.serialize(k);
                    byte[] rawValue = valueSerializer.serialize(v);
                    connection.setEx(rawKey, seconds, rawValue);
                });
                return null;
            });
        }
    }

    @Override
    public void batchEvict(Collection<String> cacheKeys) {
        if (CollectionUtils.isNotEmpty(cacheKeys)) {
            // 移除一级缓存和redis缓存
            caffeineCache.invalidateAll(cacheKeys);
            redisTemplate.delete(cacheKeys);

            cacheDelayedSynchronization.accept(cacheKeys);
        }
    }

    @Override
    public String cacheKey(Object key) {
        return cacheNamePrefix + key.toString();
    }

    /**
     * 缓存过期时间
     *
     * @return
     */
    private long getExpire() {
        Long cacheNameExpire = cacheConfigProperties.getRedisExpires().get(cacheName);
        if (cacheNameExpire != null) {
            return cacheNameExpire;
        }
        long begin = cacheConfigProperties.getTimeOutBegin();
        long end = cacheConfigProperties.getTimeOutEnd();
        return (long) (Math.random() * (end + 1 - begin) + begin);
    }

    /**
     * 轮询多级缓存
     *
     * @param cacheKey 缓存的key
     * @return 值
     */
    private Object loopCache(String cacheKey) {
        Object value = caffeineCache.getIfPresent(cacheKey);
        if (value != null) {
            log.trace("key={},命中caffeineCache(一级缓存)", cacheKey);
            return value;
        }

        Boolean hasKey = redisTemplate.hasKey(cacheKey);
        if (hasKey != null && !hasKey) {
            return null;
        }

        // 获取redis缓存的值，加入到一级缓存
        value = super.toStoreValue(redisTemplate.opsForValue().get(cacheKey));
        log.trace("key={},命中redisCache(二级缓存)", cacheKey);
        caffeineCache.put(cacheKey, value);

        return value;
    }

    /**
     * 获取锁
     *
     * @param cacheKey 缓存的key
     * @return 锁
     */
    private RLock getLock(String cacheKey) {
        return redisson.getLock(cacheKey + "RedisCaffeineCacheLock");
    }
}
