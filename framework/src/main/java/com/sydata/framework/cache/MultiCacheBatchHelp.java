package com.sydata.framework.cache;

import cn.hutool.core.util.ReflectUtil;
import com.sydata.framework.cache.batch.IMultiCacheBatch;
import com.sydata.framework.cache.batch.MultiCacheBatch;
import com.sydata.framework.cache.batch.RwLockMultiCacheBatch;
import com.sydata.framework.cache.support.RedisCaffeineCacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lzq
 * @describe 多级缓存操作工具
 * @date 2022-04-12 09:17
 */
@Component
public class MultiCacheBatchHelp {

    private final static int DEFAULT_BATCH_SIZE = 200;

    private static RedisCaffeineCacheManager cacheManager;


    @Resource
    public void setCacheManager(RedisCaffeineCacheManager cacheManager) {
        MultiCacheBatchHelp.cacheManager = cacheManager;
    }

    /**
     * 简单多级缓存批量操作
     *
     * @param cacheName 缓存名
     * @return 多级缓存批量操作标准实现
     */
    public static MultiCacheBatch apply(String cacheName) {
        return apply(cacheName, DEFAULT_BATCH_SIZE, MultiCacheBatch.class);
    }

    /**
     * 读写锁多级缓存批量操作
     *
     * @param cacheName 缓存名
     * @return 多级缓存批量操作读写锁实现
     */
    public static RwLockMultiCacheBatch applyRw(String cacheName) {
        return apply(cacheName, DEFAULT_BATCH_SIZE, RwLockMultiCacheBatch.class);
    }


    /**
     * 申请读写锁多级缓存批量操作实现类
     *
     * @param cacheName       缓存名
     * @param batchSize       批次操作大小
     * @param multiCacheClass 多级缓存批量操作顶级接口实现类（必须要有无参构造）
     * @return 读写锁多级缓存批量操作实现类
     */
    public static <T extends IMultiCacheBatch> T apply(String cacheName, int batchSize, Class<T> multiCacheClass) {
        T instance = ReflectUtil.newInstance(multiCacheClass);
        instance.init(cacheManager.getCache(cacheName), batchSize);
        return instance;
    }
}
