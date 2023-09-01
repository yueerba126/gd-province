
package com.sydata.framework.cache.redisson;

import com.sydata.framework.cache.codec.GenericJackson2JsonCodec;
import com.sydata.framework.cache.properties.RedissonProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Sentinel;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * redisson自动配置类
 * </p>
 *
 * @author lzq
 * @date 2021/7/27 15:29
 */
@Configuration
@ConditionalOnClass({Redisson.class, RedisOperations.class})
@EnableConfigurationProperties({RedissonProperties.class, RedisProperties.class})
public class RedissonAutoConfiguration {

    private static final String REDIS_PROTOCOL_PREFIX = "redis://";
    private static final String REDISS_PROTOCOL_PREFIX = "rediss://";

    @Autowired(required = false)
    private List<RedissonAutoConfigurationCustomizer> redissonAutoConfigurationCustomizers;

    @Resource
    private RedissonProperties redissonProperties;

    @Resource
    private RedisProperties redisProperties;

    @Resource
    private GenericJackson2JsonCodec genericJackson2JsonCodec;


    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redisson() {
        Config config;
        Method clusterMethod = ReflectionUtils.findMethod(RedisProperties.class, "getCluster");
        Method timeoutMethod = ReflectionUtils.findMethod(RedisProperties.class, "getTimeout");
        Object timeoutValue = ReflectionUtils.invokeMethod(timeoutMethod, redisProperties);
        int timeout;
        if (null == timeoutValue) {
            timeout = 10000;
        } else if (!(timeoutValue instanceof Integer)) {
            Method millisMethod = ReflectionUtils.findMethod(timeoutValue.getClass(), "toMillis");
            timeout = ((Long) ReflectionUtils.invokeMethod(millisMethod, timeoutValue)).intValue();
        } else {
            timeout = (Integer) timeoutValue;
        }

        if (redisProperties.getSentinel() != null) {
            Method nodesMethod = ReflectionUtils.findMethod(Sentinel.class, "getNodes");
            Object nodesValue = ReflectionUtils.invokeMethod(nodesMethod, redisProperties.getSentinel());

            String[] nodes;
            if (nodesValue instanceof String) {
                nodes = convert(Arrays.asList(((String) nodesValue).split(",")));
            } else {
                nodes = convert((List<String>) nodesValue);
            }
            config = new Config();
            config.useSentinelServers()
                    .setMasterName(redisProperties.getSentinel().getMaster())
                    .addSentinelAddress(nodes)
                    .setDatabase(redisProperties.getDatabase())
                    .setConnectTimeout(timeout)
                    .setPassword(redisProperties.getPassword());
        } else if (clusterMethod != null && ReflectionUtils.invokeMethod(clusterMethod, redisProperties) != null) {
            Object clusterObject = ReflectionUtils.invokeMethod(clusterMethod, redisProperties);
            Method nodesMethod = ReflectionUtils.findMethod(clusterObject.getClass(), "getNodes");
            List<String> nodesObject = (List) ReflectionUtils.invokeMethod(nodesMethod, clusterObject);

            String[] nodes = convert(nodesObject);

            config = new Config();
            config.useClusterServers()
                    .addNodeAddress(nodes)
                    .setConnectTimeout(timeout)
                    .setPassword(redisProperties.getPassword());
        } else {
            config = new Config();
            String prefix = REDIS_PROTOCOL_PREFIX;
            Method method = ReflectionUtils.findMethod(RedisProperties.class, "isSsl");
            if (method != null && (Boolean) ReflectionUtils.invokeMethod(method, redisProperties)) {
                prefix = REDISS_PROTOCOL_PREFIX;
            }

            config.useSingleServer()
                    .setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
                    .setConnectTimeout(timeout)
                    .setDatabase(redisProperties.getDatabase())
                    .setPassword(redisProperties.getPassword());
        }

        if (redissonAutoConfigurationCustomizers != null) {
            for (RedissonAutoConfigurationCustomizer customizer : redissonAutoConfigurationCustomizers) {
                customizer.customize(config);
            }
        }

        config.setCodec(genericJackson2JsonCodec);
        config.setThreads(redissonProperties.getThreads()).setNettyThreads(redissonProperties.getNettyThreads());
        return Redisson.create(config);
    }

    private String[] convert(List<String> nodesObject) {
        List<String> nodes = new ArrayList<String>(nodesObject.size());
        for (String node : nodesObject) {
            if (!node.startsWith(REDIS_PROTOCOL_PREFIX) && !node.startsWith(REDISS_PROTOCOL_PREFIX)) {
                nodes.add(REDIS_PROTOCOL_PREFIX + node);
            } else {
                nodes.add(node);
            }
        }
        return nodes.toArray(new String[nodes.size()]);
    }
}
