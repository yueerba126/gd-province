package com.sydata.framework.cache.batch;

import cn.hutool.core.util.ReflectUtil;
import com.sydata.framework.cache.execute.AbstractCacheComposeExecute;
import com.sydata.framework.cache.execute.CacheComposeExecute;
import com.sydata.framework.cache.support.MultiCache;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author lzq
 * @describe 多级缓存批量操作顶级接口, 大key容易阻塞redis工作线程从而导致redis性能低下, 所以尽管是批量操作底层都是采用分批进行
 * @date 2022-04-12 09:29
 */
public interface IMultiCacheBatch {

    /**
     * 初始化
     *
     * @param multiCache 多级缓存顶级接口
     * @param batchSize  批次数
     */
    void init(MultiCache multiCache, int batchSize);

    /**
     * 多级缓存
     *
     * @return 多级缓存
     */
    MultiCache multiCache();

    /**
     * 每批次大小
     *
     * @return
     */
    int batchSize();

    /**
     * 缓存的keys
     *
     * @param keys 不带前缀的keys
     * @return 缓存的keys
     */
    List<String> cacheKeys(Collection<String> keys);

    /**
     * 批量查询缓存
     *
     * @param cacheKeys 缓存的keys
     * @return 缓存的值
     */
    <V> Map<String, V> batchGet(Collection<String> cacheKeys);

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
     * 转换为多级缓存组合操作执行器-标准实现
     *
     * @return 多级缓存组合操作执行器-标准实现
     */
    default <P, M, V> CacheComposeExecute<P, M, V> composeExecute() {
        return composeExecute(CacheComposeExecute.class);
    }


    /**
     * 转换多级缓存组合操作执行器-指定实现
     *
     * @param classes 指定实现类
     * @return 多级缓存组合操作执行器-指定实现
     */
    default <T extends AbstractCacheComposeExecute> T composeExecute(Class<T> classes) {
        T cacheComposeExecute = ReflectUtil.newInstance(classes);
        cacheComposeExecute.multiCacheBatch(this);
        return cacheComposeExecute;
    }
}
