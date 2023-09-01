package com.sydata.framework.cache.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 缓存模块全局配置
 * </p>
 *
 * @author lzq
 * @date 2021/7/27 15:29
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "zt.cache")
public class CacheConfigProperties {

    /**
     * 缓存key的分隔符
     */
    public final static String KEY_SEGMENTATION = ":";

    /**
     * 缓存前缀
     */
    public final static String CACHE_PREFIX = "RedisCaffeineCache";

    /**
     * 是否存储空值，默认true，防止缓存穿透
     */
    private boolean cacheNullValues = true;

    /**
     * 本地缓存异步驱逐延时时长（默认1秒）
     */
    private long cacheAsyExpelDelay = 1;

    /**
     * 访问后过期时间，单位秒（默认1小时）
     */
    private long caffeineExpireAfterAccess = 1 * 60 * 60;

    /**
     * 写入后过期时间，单位秒（默认1小时）
     */
    private long caffeineExpireAfterWrite = 1 * 60 * 60;

    /**
     * 初始化大小（默认20）
     */
    private int caffeineInitialCapacity = 20;

    /**
     * 最大缓存对象个数，超过此数量时之前放入的缓存将失效（默认10240）
     */
    private int caffeineMaximumSize = 1024 * 10;

    /**
     * 每个cacheName的过期时间，单位毫秒，优先级比defaultExpiration高
     */
    private Map<String, Long> redisExpires = new HashMap<>();

    /**
     * 开始随机过期时间（默认7天）
     */
    private long timeOutBegin = 7 * 24 * 60 * 60;

    /**
     * 结束随机过期时间（默认30天）
     */
    private long timeOutEnd = 30 * 24 * 60 * 60;
}
