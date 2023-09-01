package com.sydata.framework.cache.support;

import org.springframework.cache.Cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 多级缓存顶级接口
 *
 * @author lzq
 * @date 2021/7/29 14:10
 */
public interface MultiCache extends Cache {

    /**
     * 批量查询缓存
     *
     * @param cacheKeys 缓存的keys
     * @return 缓存的值
     */
    <V> Map<String, V> batchGet(List<String> cacheKeys);

    /**
     * 批量设置缓存
     *
     * @param map 批量缓存
     */
    <V> void batchPut(Map<String, V> map);

    /**
     * 批量移除缓存
     *
     * @param cacheKeys 缓存的keys
     */
    void batchEvict(Collection<String> cacheKeys);

    /**
     * 转换成缓存Key
     *
     * @param key 原始key
     * @return 缓存key
     */
    String cacheKey(Object key);
}
