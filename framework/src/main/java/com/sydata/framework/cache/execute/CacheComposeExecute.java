package com.sydata.framework.cache.execute;


import com.sydata.framework.cache.batch.IMultiCacheBatch;
import com.sydata.framework.cache.util.FieldNameFunction;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.util.Assert;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author lzq
 * @describe 多级缓存组合操作执行器-标准实现
 * @date 2022-04-12 20:24
 */
public class CacheComposeExecute<P, M, V> extends AbstractCacheComposeExecute<P, M, V> {

    protected Function<Collection<P>, Collection<M>> targets;

    protected Function<Collection<M>, Map<P, V>> group;


    @Override
    public CacheComposeExecute<P, M, V> inline() {
        return (CacheComposeExecute<P, M, V>) super.inline();
    }

    @Override
    public CacheComposeExecute<P, M, V> multiCacheBatch(IMultiCacheBatch multiCacheBatch) {
        return (CacheComposeExecute<P, M, V>) super.multiCacheBatch(multiCacheBatch);
    }

    @Override
    public CacheComposeExecute<P, M, V> fields(FieldNameFunction<M, Object>... fields) {
        return (CacheComposeExecute<P, M, V>) super.fields(fields);
    }

    @Override
    public CacheComposeExecute<P, M, V> params(Collection<P> params) {
        return (CacheComposeExecute<P, M, V>) super.params(params);
    }

    @Override
    public CacheComposeExecute<P, M, V> paddings(Object... paddings) {
        return (CacheComposeExecute<P, M, V>) super.paddings(paddings);
    }

    public CacheComposeExecute<P, M, V> targets(Function<Collection<P>, Collection<M>> targets) {
        this.targets = targets;
        return this;
    }

    public CacheComposeExecute<P, M, V> group(Function<Collection<M>, Map<P, V>> group) {
        this.group = group;
        return this;
    }


    @Override
    public Collection<V> get() {
        Assert.notEmpty(params, "查询参数不能为空");
        Assert.notEmpty(fields, "缓存列不能为空");

        // 获取所有列名
        String[] fieldNames = fieldNames();

        // 查询缓存
        Map<String, List<P>> keyMap = StreamEx.of(params)
                .groupingBy(param -> {
                    Object[] fieldValues = fieldValues(param, fieldNames);
                    String key = joinKey(fieldNames, fieldValues);
                    String cacheKey = multiCacheBatch.multiCache().cacheKey(key);
                    return cacheKey;
                });
        Map<String, Object> cacheMap = multiCacheBatch.batchGet(keyMap.keySet());

        // 过滤未命中缓存的key并且去重，批量回调查询目标集合
        if (cacheMap.size() != keyMap.size()) {
            cacheMap.keySet().forEach(keyMap::remove);

            Collection<M> targets = StreamEx.of(keyMap.values())
                    .filter(CollectionUtils::isNotEmpty)
                    .map(ps -> ps.get(0))
                    .distinct()
                    .toListAndThen(this.targets::apply);

            Map<String, V> targetMap = put(() -> group.apply(targets));
            cacheMap.putAll(targetMap);
        }
        return (Collection<V>) cacheMap.values();
    }

    @Override
    public Map<String, V> put(Supplier<Map<P, V>> kvSupplier) {
        Map<P, V> kvMap = kvSupplier.get();
        if (MapUtils.isEmpty(kvMap)) {
            return Collections.emptyMap();
        }

        Assert.notEmpty(fields, "缓存列不能为空");

        String[] fieldNames = fieldNames();
        Map<String, Object> targetMap = new HashMap<>(kvMap.size() * 2);
        kvMap.forEach((p, v) -> {
            Object[] fieldValues = fieldValues(p, fieldNames);
            String key = joinKey(fieldNames, fieldValues);
            String cacheKey = multiCacheBatch.multiCache().cacheKey(key);
            targetMap.put(cacheKey, v);
        });
        multiCacheBatch.batchPut(targetMap);
        return (Map<String, V>) targetMap;
    }


    @Override
    public void remove() {
        Assert.notEmpty(params, "查询参数不能为空");
        Assert.notEmpty(fields, "缓存列不能为空");

        String[] fieldNames = fieldNames();
        Set<String> cacheKeys = StreamEx.of(params)
                .map(param -> {
                    Object[] fieldValues = fieldValues(param, fieldNames);
                    String key = joinKey(fieldNames, fieldValues);
                    String cacheKey = multiCacheBatch.multiCache().cacheKey(key);
                    return cacheKey;
                })
                .toSet();
        if (CollectionUtils.isNotEmpty(cacheKeys)) {
            multiCacheBatch.batchEvict(cacheKeys);
        }
    }
}
