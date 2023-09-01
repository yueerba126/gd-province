package com.sydata.framework.databind.utils;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.sydata.framework.cache.util.ClassFieldMapUtil;
import com.sydata.framework.databind.annotation.DataBindFieldConfig;
import com.sydata.framework.databind.domain.DataBindField;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.domain.DataBindSourceData;
import com.sydata.framework.databind.domain.Empty;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.databind.handle.value.bind.IValueStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * 数据绑定工具类
 *
 * @author zhoucl
 * @date 2021/6/25 14:25
 */
@Slf4j
@Component
public class DataBindUtils {

    /**
     * key类型转换
     *
     * @param key     原始key值
     * @param keyType key类型class
     * @return 转换后值
     */
    public static Object keyTypeCast(Object key, Class<?> keyType) {
        return TypeUtils.cast(key, keyType, ParserConfig.getGlobalInstance());
    }

    /**
     * 获取对象对应唯一key以及原始对象
     *
     * @param strategyData 唯一key包装对象
     * @param function     自定义接受结果
     */
    public static Object convertStrategyData(Object strategyData, BiFunction<Object, String, Object> function) {
        String unKey = null;
        // 取到方案需要取的远端字段信息
        if (strategyData instanceof DataBindSourceData) {
            DataBindSourceData dataBindSourceData = (DataBindSourceData) strategyData;
            strategyData = dataBindSourceData.getData();
            unKey = dataBindSourceData.getUnKey();
        }
        if (function != null) {
            return function.apply(strategyData, unKey);
        }
        return null;
    }

    /**
     * 获取对象对应唯一key以及原始对象
     *
     * @param strategyData 唯一key包装对象
     * @param function     自定义接受结果
     */
    public static void convertStrategyData(Object strategyData, BiConsumer<Object, String> function) {
        convertStrategyData(strategyData, (data, unKey) -> {
            function.accept(data, unKey);
            return null;
        });
    }

    /**
     * 获取拼接后key结果
     *
     * @param dataBindFieldConfig 本次数据绑定配置注解
     * @param queryAndSql         查询时附带组合条件
     * @param sourceFieldValue    原字段值
     * @return 远程数据查询参数
     */
    public static DataBindQuery buildDataBindQuery(DataBindFieldConfig dataBindFieldConfig,
                                                   String queryAndSql,
                                                   Object sourceFieldValue) {
        // sourceFieldCombination:字典需要拼接规定的类型字符串，比如：quality_level,1
        Object queryKey = sourceFieldValue;
        if (dataBindFieldConfig.sourceDataType() != Empty.class) {
            queryKey = TypeUtils.cast(queryKey, dataBindFieldConfig.sourceDataType(), ParserConfig.getGlobalInstance());
        }
        return new DataBindQuery()
                .setQueryColumn(dataBindFieldConfig.queryColumn())
                .setQueryAndSql(queryAndSql)
                .setQueryKey(queryKey)
                .setSourceFieldCombination(dataBindFieldConfig.sourceFieldCombination())
                .setTenantId(DataBindHandleBootstrap.getCurrentThreadTenantId());
    }

    /**
     * @param queryColumn            查询字段
     * @param queryAndSql            查询时附带组合条件
     * @param sourceFieldValue       原字段值
     * @param sourceFieldCombination 原字段值组合字符
     * @return 远程数据查询参数
     */
    public static DataBindQuery buildDataBindQuery(String queryColumn,
                                                   String queryAndSql,
                                                   String sourceFieldValue,
                                                   String sourceFieldCombination) {
        return new DataBindQuery()
                .setQueryColumn(queryColumn)
                .setQueryAndSql(queryAndSql)
                .setQueryKey(sourceFieldValue)
                .setSourceFieldCombination(sourceFieldCombination)
                .setTenantId(DataBindHandleBootstrap.getCurrentThreadTenantId());
    }

    /**
     * 解析查询附加条件
     *
     * @param dataBindFieldConfig 注解配置对象
     * @param data                字段所在类
     * @return 返回解析后的条件语句
     */
    public static String parseExpressionQueryAndSql(DataBindFieldConfig dataBindFieldConfig, Object data) {
        return parseExpressionQueryAndSql(dataBindFieldConfig.queryAndSql(), dataBindFieldConfig.queryAndSqlColumns(), data);
    }

    /**
     * 解析查询附加条件
     *
     * @param queryAndSql 查询时附带组合条件
     * @param columnsEnum 拼装sql提供字段枚举
     * @param data        字段所在类
     * @return 返回解析后的条件语句
     */
    public static String parseExpressionQueryAndSql(String queryAndSql, Class<? extends Enum<?>> columnsEnum, Object data) {
        return null;
//        if (StringUtils.isNotEmpty(queryAndSql) && columnsEnum != Empty.class) {
//            try {
//                Method method = columnsEnum.getDeclaredMethod(IValueStrategy.QUERY_AND_SQL_COLUMNS_METHOD_NAME);
//                List<DataBindField> dataBindFields = (List<DataBindField>) method.invoke(null, ArrayUtils.EMPTY_OBJECT_ARRAY);
//                return (String) DataBindHandleBootstrap.getFieldValue(data, queryAndSql, evaluationContext -> {
//                    for (DataBindField dataBindField : dataBindFields) {
//                        evaluationContext.setVariable(dataBindField.getPropertyName(), dataBindField);
//                    }
//                });
//            } catch (Exception e) {
//                log.error("获取枚举提供数据库字段异常", e);
//            }
//        }
//        return queryAndSql;
    }

    /**
     * 清除数据绑定多字段缓存
     *
     * @param columnsEnum 字段枚举
     * @param tenantIds   数据隔离组织id
     * @param data        修改前的基础数据
     * @param consumer    组装好的数据绑定对象
     */
    public static void clearQueryColumnCache(Class<? extends Enum<?>> columnsEnum, List<Long> tenantIds, Object data, Consumer<DataBindQuery> consumer) {
        if (columnsEnum != Empty.class && consumer != null) {
            try {
                // 获取到枚举中配置支持的查询字段并在基础数据对象中或取出对应的值
                Method method = columnsEnum.getDeclaredMethod(IValueStrategy.QUERY_AND_SQL_COLUMNS_METHOD_NAME);
                List<DataBindField> dataBindFields = (List<DataBindField>) method.invoke(null, ArrayUtils.EMPTY_OBJECT_ARRAY);

                // 组装清除缓存需要的组织id以及查询数据，交到调用方去清除缓存
                DataBindQuery dataBindQuery = new DataBindQuery();
                if (CollectionUtils.isNotEmpty(tenantIds)) {
                    tenantIds.forEach(tenantId -> {
                        dataBindQuery.setTenantId(tenantId);
                        dataBindFields.forEach(dataBindField -> {
                            dataBindQuery.setQueryKey(ClassFieldMapUtil.getFieldVal(data, dataBindField.getFieldName()));
                            dataBindQuery.setQueryColumn(dataBindField.getColumnName());
                            consumer.accept(dataBindQuery);
                        });
                    });
                } else {
                    dataBindFields.forEach(dataBindField -> {
                        dataBindQuery.setQueryKey(ClassFieldMapUtil.getFieldVal(data, dataBindField.getFieldName()));
                        dataBindQuery.setQueryColumn(dataBindField.getColumnName());
                        consumer.accept(dataBindQuery);
                    });
                }
            } catch (Exception e) {
                log.error("获取枚举提供数据库字段异常", e);
            }
        }
    }

    /**
     * @see DataBindUtils#clearQueryColumnCache(Class, List, Object, Consumer)
     */
    public static void clearQueryColumnCache(Class<? extends Enum<?>> columnsEnum, Long tenantId, Object data, Consumer<DataBindQuery> consumer) {
        clearQueryColumnCache(columnsEnum, Collections.singletonList(tenantId), data, consumer);
    }

}
