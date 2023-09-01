package com.sydata.framework.cache.batch;

import com.sydata.framework.cache.support.MultiCache;
import org.apache.commons.collections4.ListUtils;

import java.util.*;

/**
 * @author lzq
 * @describe 多级缓存批量操作-标准实现（适用场景:在一定时间内不在乎数据是否一致,要求响应优先）
 * @date 2022-04-12 09:26
 */
public class MultiCacheBatch implements IMultiCacheBatch {

    private MultiCache multiCache;

    private int batchSize;

    @Override
    public void init(MultiCache multiCache, int batchSize) {
        this.multiCache = multiCache;
        this.batchSize = batchSize;
    }

    @Override
    public MultiCache multiCache() {
        return multiCache;
    }

    @Override
    public int batchSize() {
        return batchSize;
    }

    @Override
    public List<String> cacheKeys(Collection<String> keys) {
        List<String> cacheKeys = new ArrayList<>();
        keys.forEach(key -> cacheKeys.add(multiCache.cacheKey(key)));
        return cacheKeys;
    }

    @Override
    public <V> Map<String, V> batchGet(Collection<String> cacheKeys) {
        // 根据分批大小进行分区后分批get
        List<List<String>> partition = ListUtils.partition(new ArrayList<>(cacheKeys), batchSize);

        Map<String, Object> cacheMap = new HashMap<>(cacheKeys.size() * 2);
        partition.forEach(batchCacheKeys -> {
            Map<String, Object> cache = multiCache.batchGet(batchCacheKeys);
            cacheMap.putAll(cache);
        });
        return (Map<String, V>) cacheMap;
    }

    @Override
    public <V> void batchPut(Map<String, V> map) {
        // 根据分批大小对key进行分区后分批put
        List<List<String>> partition = ListUtils.partition(new ArrayList<>(map.keySet()), batchSize);

        partition.forEach(cacheKeys -> {
            Map<String, Object> batchMap = new HashMap<>(cacheKeys.size() * 2);
            cacheKeys.forEach(cacheKey -> batchMap.put(cacheKey, map.get(cacheKey)));
            multiCache.batchPut(batchMap);
        });
    }

    @Override
    public void batchEvict(Collection<String> cacheKeys) {
        // 根据分批大小进行分区分批evict
        List<List<String>> partition = ListUtils.partition(new ArrayList<>(cacheKeys), batchSize);
        partition.forEach(multiCache::batchEvict);
    }
}
