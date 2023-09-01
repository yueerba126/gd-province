package com.sydata.framework.databind.handle.value.bind.impl;

import com.sydata.framework.databind.annotation.DataBindFieldConfig;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.domain.DataBindSourceData;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.databind.handle.strategy.IDataBindStrategy;
import com.sydata.framework.databind.handle.value.bind.IValueStrategy;
import com.sydata.framework.databind.utils.DataBindUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * DataBindFieldConfig方式数据绑定基类
 *
 * @author zhoucl
 * @date 2021/5/25 19:29
 */
@Slf4j
public abstract class AbstractDataBindFieldConfigValueBindStrategy implements IValueStrategy<DataBindFieldConfig> {

    @Override
    public void bindQueryKey(DataBindFieldConfig dataBindFieldConfig,
                             Class<? extends Annotation> dataBindStrategy,
                             Map<Class<? extends Annotation>, HashSet<DataBindQuery>> dataBindStrategyDataIdsMap,
                             Map<Class<? extends Annotation>, IDataBindStrategy> dataBindStrategyMap,
                             Field bindField,
                             DataBindSourceData sourceData) {
        // 或去除本地对象id字段值
        Object sourceFieldValue = DataBindHandleBootstrap.getFieldValue(sourceData.getData(), sourceData.getUnKey(), dataBindFieldConfig.sourceField());
        if (Objects.nonNull(sourceFieldValue)) {
            // 方案+对应ids
            Set<DataBindQuery> dataBindQuerys = dataBindStrategyDataIdsMap.computeIfAbsent(dataBindStrategy, k -> new HashSet<>());
            // 交给子类绑定id
            this.doBindQueryKey(dataBindFieldConfig,
                    dataBindQuerys,
                    DataBindUtils.parseExpressionQueryAndSql(dataBindFieldConfig, sourceData.getData()),
                    bindField,
                    sourceData,
                    sourceFieldValue);
        }
    }

    @Override
    public void buildValue(DataBindFieldConfig dataBindFieldConfig,
                           Class<? extends Annotation> dataBindStrategy,
                           Field bindField,
                           Map<Class<? extends Annotation>, Map<?, ?>> dataBindStrategyDataMap,
                           Map<Class<? extends Annotation>, IDataBindStrategy> dataBindStrategyMap,
                           DataBindSourceData sourceData) {
        if (Objects.isNull(getBindFieldValue(bindField, sourceData))) {
            // 本次循环到的方案map
            Map<?, ?> datasMap = dataBindStrategyDataMap.get(dataBindStrategy);
            if (MapUtils.isNotEmpty(datasMap)) {
                // 获取本地对象id字段值
                Object sourceFieldValue = DataBindHandleBootstrap.getFieldValue(sourceData.getData(), sourceData.getUnKey(), dataBindFieldConfig.sourceField());
                if (Objects.nonNull(sourceFieldValue)) {

                    // 交给子类构建结果数据
                    Object buildValue = this.getBuildValue(
                            dataBindFieldConfig,
                            datasMap,
                            sourceFieldValue,
                            DataBindUtils.parseExpressionQueryAndSql(dataBindFieldConfig, sourceData.getData()));

                    // 赋值到本地对象
                    if (Objects.nonNull(buildValue)) {
                        DataBindHandleBootstrap.setValueField(sourceData.getData(), sourceData.getUnKey(), buildValue, bindField);
                    }
                }
            }
        }
    }

    /**
     * 解析源字段值放入查询id列表
     *
     * @param dataBindFieldConfig 本次数据绑定配置注解
     * @param dataBindQuerys      源数据每种数据类型查询对象集合
     * @param queryAndSql         查询时附带组合条件
     * @param bindField           绑定对象字段
     * @param sourceData          来源包装对象
     * @param sourceFieldValue    源数据字段值
     */
    abstract void doBindQueryKey(DataBindFieldConfig dataBindFieldConfig,
                                 Set<DataBindQuery> dataBindQuerys,
                                 String queryAndSql,
                                 Field bindField,
                                 DataBindSourceData sourceData,
                                 Object sourceFieldValue);

    /**
     * 通过源字段值构建出业务方需要字段值
     *
     * @param dataBindFieldConfig 本次数据绑定配置注解
     * @param datasMap            查询到的类型数据
     * @param sourceFieldValue    原字段值
     * @param queryAndSql         查询时附带组合条件
     * @return 构建后的数据
     */
    abstract Object getBuildValue(DataBindFieldConfig dataBindFieldConfig,
                                  Map<?, ?> datasMap,
                                  Object sourceFieldValue,
                                  String queryAndSql);
}
