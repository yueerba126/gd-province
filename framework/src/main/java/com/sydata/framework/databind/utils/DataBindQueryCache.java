package com.sydata.framework.databind.utils;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.cache.batch.MultiCacheBatch;
import com.sydata.framework.cache.util.ClassFieldMapUtil;
import com.sydata.framework.databind.domain.DataBindQuery;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.baomidou.mybatisplus.core.toolkit.StringUtils.underlineToCamel;
import static java.lang.Boolean.TRUE;

/**
 * 查询数据绑定需要数据
 *
 * @author zhoucl
 * @date 2021/7/15 15:08
 */
public class DataBindQueryCache<T> {

    /**
     * 数据绑定缓存构建
     *
     * @param dataBindQuerys 本次数据绑定查询对象
     * @param cacheName      缓存名
     */
    public static <T> DataBindQueryCache<T> build(Collection<DataBindQuery> dataBindQuerys, String cacheName) {
        return new DataBindQueryCache<>(dataBindQuerys, cacheName);
    }

    /**
     * 本次数据绑定查询对象
     */
    private Collection<DataBindQuery> dataBindQuerys;

    /**
     * 多级缓存批量操作
     */
    private MultiCacheBatch multiCacheBatch;

    /**
     * 构建查询key
     */
    private Function<DataBindQuery, String> buildCacheKeyFun;

    /**
     * 构建查询到的数据缓存key
     */
    private BiFunction<Object, T, String> buildQueryDataCacheKeyFun;

    /**
     * 查询到的数据去重
     */
    private BiFunction<Object, T, ?> queryDataDistinctFun;

    /**
     * 查询数据库数据
     */
    private BiFunction<Object, List<DataBindQuery>, List<T>> queryDataServiceFun;

    /**
     * 数据绑定缓存构建
     *
     * @param dataBindQuerys 本次数据绑定查询对象
     * @param cacheName      缓存名
     */
    public DataBindQueryCache(Collection<DataBindQuery> dataBindQuerys, String cacheName) {
        this.dataBindQuerys = dataBindQuerys;
        this.multiCacheBatch = MultiCacheBatchHelp.apply(cacheName);
    }

    /**
     * 构建查询key
     *
     * @param buildCacheKeyFun 构建查询key回调
     */
    public DataBindQueryCache<T> buildCacheKey(Function<DataBindQuery, String> buildCacheKeyFun) {
        this.buildCacheKeyFun = buildCacheKeyFun;
        return this;
    }


    /**
     * 查询数据库数据
     *
     * @param queryDataServiceFun 查询数据库数据回调
     */
    public DataBindQueryCache<T> selectByQueryColumns(BiFunction<Object, List<DataBindQuery>, List<T>> queryDataServiceFun) {
        this.queryDataServiceFun = queryDataServiceFun;
        return this;
    }

    /**
     * 构建查询到的数据缓存key
     *
     * @param buildQueryDataCacheKeyFun 构建查询到的数据缓存key回调
     */
    public DataBindQueryCache<T> buildQueryDataCacheKey(BiFunction<Object, T, String> buildQueryDataCacheKeyFun) {
        this.buildQueryDataCacheKeyFun = buildQueryDataCacheKeyFun;
        return this;
    }

    /**
     * 查询到的数据去重
     *
     * @param queryDataDistinctFun 构建查询到的数据缓存key回调
     */
    public DataBindQueryCache<T> queryDataDistinctFun(BiFunction<Object, T, ?> queryDataDistinctFun) {
        this.queryDataDistinctFun = queryDataDistinctFun;
        return this;
    }


    /**
     * 开始处理数据缓存
     */
    public void hand() {
        if (CollectionUtils.isNotEmpty(dataBindQuerys)) {
            // 构建批量获取缓存key
            List<String> redisKeys = StreamEx.of(dataBindQuerys)
                    // 如果查询值是Integer转换成Long;我们系统id字段全部使用的Long类型
                    .peek(dataBindQuery -> {
                        if (dataBindQuery.getQueryKey() instanceof Integer) {
                            dataBindQuery.setQueryKey(NumberUtils.convertNumberToTargetClass((Integer) dataBindQuery.getQueryKey(), Long.class));
                        }
                    })
                    .map(dataBindQuery -> {
                        // 构建好缓存key放入对象，后面使用
                        String cacheKey = multiCacheBatch.multiCache().cacheKey(buildCacheKeyFun.apply(dataBindQuery));
                        dataBindQuery.setCacheKey(cacheKey);
                        return dataBindQuery.getCacheKey();
                    })
                    .distinct()
                    .toList();
            // 命中缓存的对象
            Map<String, T> cacheMap = multiCacheBatch.batchGet(redisKeys);
            StreamEx.of(dataBindQuerys)
                    // 把命中缓存的排除
                    .filter(dataBindQuery -> !cacheMap.containsKey(dataBindQuery.getCacheKey()))
                    // 不同字段批量查询
                    .groupingBy(DataBindQuery::getQueryColumn)
                    .forEach((queryColumn, value) -> {
                        // 查询到数据转成成map
                        List<T> dataList = queryDataServiceFun.apply(queryColumn, value);
                        if (CollectionUtils.isNotEmpty(dataList)) {
                            StreamEx<T> streamEx = StreamEx.of(dataList);
                            if (queryDataDistinctFun != null) {
                                streamEx = streamEx.distinct(t -> queryDataDistinctFun.apply(queryColumn, t));
                            }

                            // 重新放入缓存
                            Map<String, T> dataMap = streamEx.toMap(data -> {
                                String key = buildQueryDataCacheKeyFun.apply(queryColumn, data);
                                return multiCacheBatch.multiCache().cacheKey(key);
                            }, d -> d);

                            // 判断是否允许设置缓存（默认允许）
                            Boolean put = DataBindCachePutUtil.get();
                            if (put == null || TRUE.equals(put)) {
                                multiCacheBatch.batchPut(dataMap);
                            }
                            cacheMap.putAll(dataMap);
                        }
                    });
            // 缓存对赋值到返回对象
            dataBindQuerys.forEach(dataBindQuery -> {
                T data = cacheMap.get(dataBindQuery.getCacheKey());
                if (data != null) {
                    dataBindQuery.setData(data);
                }
            });
        }
    }

    /**
     * 根据查询数据库列获取对象属性值
     *
     * @param queryColumn 查询数据库列
     * @param data        本次取数据对象
     * @return 字段值
     */
    public static Object getObjValue(Object queryColumn, Object data) {
        return ClassFieldMapUtil.getFieldVal(data, underlineToCamel(queryColumn.toString()));
    }

    /**
     * 数据绑定字段名/值缓存构建器
     *
     * @param cacheName      缓存名
     * @param dataBindQuerys 查询列表
     * @param baseMapper     mapper
     */
    public static <T> void dataBindFieldCache(String cacheName,
                                              Collection<DataBindQuery> dataBindQuerys,
                                              BaseMapper<T> baseMapper) {
        dataBindFieldCache(cacheName, dataBindQuerys, (column, list) -> {
            // 查询数据库
            List<Object> values = StreamEx.of(list).map(DataBindQuery::getQueryKey).toList();
            return ChainWrappers.queryChain(baseMapper).in((String) column, values).list();
        });
    }

    /**
     * 数据绑定字段名/值缓存构建器
     *
     * @param cacheName           缓存名
     * @param dataBindQuerys      查询列表
     * @param queryDataServiceFun 数据来源
     */
    public static <T> void dataBindFieldCache(String cacheName,
                                              Collection<DataBindQuery> dataBindQuerys,
                                              BiFunction<Object, List<DataBindQuery>, List<T>> queryDataServiceFun) {
        DataBindQueryCache.<T>build(dataBindQuerys, cacheName)
                .buildCacheKey(dataBindQuery -> {
                    String fieldName = underlineToCamel(dataBindQuery.getQueryColumn().toString());
                    return String.format("%s=%s", fieldName, dataBindQuery.getQueryKey());
                })
                .selectByQueryColumns(queryDataServiceFun)
                .queryDataDistinctFun((queryColumn, data) -> {
                    Object objValue = DataBindQueryCache.getObjValue(queryColumn, data);
                    return objValue.toString();
                })
                .buildQueryDataCacheKey((queryColumn, data) -> {
                    String fieldName = underlineToCamel(queryColumn.toString());
                    Object objValue = DataBindQueryCache.getObjValue(queryColumn, data);
                    return String.format("%s=%s", fieldName, objValue);
                }).hand();
    }
}
