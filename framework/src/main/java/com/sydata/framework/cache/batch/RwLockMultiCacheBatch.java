package com.sydata.framework.cache.batch;

import cn.hutool.extra.spring.SpringUtil;
import com.sydata.framework.cache.support.MultiCache;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;

import java.util.Collection;
import java.util.Map;

import static com.sydata.framework.cache.properties.CacheConfigProperties.KEY_SEGMENTATION;

/**
 * @author lzq
 * @describe 多级缓存批量操作-读写锁实现（适用场景:有数据强一致性要求 注意：写锁时会阻塞所有读操作,写的并行度不高）
 * @date 2022-04-12 09:26
 */
public class RwLockMultiCacheBatch extends MultiCacheBatch {

    private RReadWriteLock readWriteLock;

    @Override
    public void init(MultiCache multiCache, int batchSize) {
        super.init(multiCache, batchSize);

        // 根据服务名+缓存类名+缓存名拼而成的读写锁key
        String lockMark = SpringUtil.getApplicationName() + KEY_SEGMENTATION
                + RwLockMultiCacheBatch.class.getSimpleName() + KEY_SEGMENTATION
                + multiCache.getName();
        this.readWriteLock = SpringUtil.getBean(RedissonClient.class).getReadWriteLock(lockMark);
    }


    @Override
    public <V> Map<String, V> batchGet(Collection<String> cacheKeys) {
        RLock lock = readWriteLock.readLock();
        try {
            lock.lock();
            return super.batchGet(cacheKeys);
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Override
    public <V> void batchPut(Map<String, V> map) {
        RLock lock = readWriteLock.writeLock();
        try {
            lock.lock();
            super.batchPut(map);
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Override
    public void batchEvict(Collection<String> cacheKeys) {
        RLock lock = readWriteLock.writeLock();
        try {
            lock.lock();
            super.batchEvict(cacheKeys);
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
