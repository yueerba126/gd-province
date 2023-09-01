package com.sydata.framework.cache.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * redisson配置类
 * </p>
 *
 * @author lzq
 * @date 2021/7/27 15:29
 */
@Data
@ConfigurationProperties(prefix = "spring.redis.redisson")
public class RedissonProperties {

    /**
     * 线程数
     */
    private int threads = 16;

    /**
     * netty线程数
     */
    private int nettyThreads = 32;
}
