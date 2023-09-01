package com.sydata.framework.cache;

import com.sydata.framework.cache.config.RedisSerializerConfig;
import com.sydata.framework.cache.properties.CacheConfigProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

/**
 * <p>
 * 二级缓存自动配置类
 * </p>
 *
 * @author lzq
 * @date 2021/7/27 15:29
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CacheConfigProperties.class)
@ConditionalOnBean(RedisSerializerConfig.class)
@AutoConfigureBefore(RedisAutoConfiguration.class)
@EnableCaching
public class MultiCacheAutoConfiguration {


    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Resource
    private RedisSerializer jackson2JsonRedisSerializer;

    @Primary
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        return createRedisTemplate();
    }

    @Bean
    public RedisTemplate<String, Object> transactionRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = createRedisTemplate();
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(Executor taskExecutor) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.setTaskExecutor(taskExecutor);
        return redisMessageListenerContainer;
    }


    /**
     * 创建RedisTemplate
     *
     * @return RedisTemplate
     */
    private RedisTemplate<String, Object> createRedisTemplate() {
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 默认序列化
        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);

        // key序列化
        redisTemplate.setKeySerializer(RedisSerializer.string());
        // value序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // Hash key序列化
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        // Hash value序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
