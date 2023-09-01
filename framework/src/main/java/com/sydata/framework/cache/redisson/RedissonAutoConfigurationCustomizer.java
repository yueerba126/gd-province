package com.sydata.framework.cache.redisson;

import org.redisson.config.Config;

/**
 * <p>
 * redisson配置增强接口
 * </p>
 *
 * @author lzq
 * @date 2021/7/27 15:29
 */
@FunctionalInterface
public interface RedissonAutoConfigurationCustomizer {

    /**
     * Customize the RedissonClient configuration.
     *
     * @param configuration the {@link Config} to customize
     */
    void customize(final Config configuration);
}
