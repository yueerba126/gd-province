package com.sydata.framework.databind.handle.value.bind.impl;

import com.sydata.framework.databind.annotation.DataBindFieldGroupConfig;
import com.sydata.framework.databind.annotation.DataBindFieldGroupItemConfig;
import com.sydata.framework.databind.config.DataBindConstants;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.domain.DataBindSourceData;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.databind.handle.strategy.IDataBindStrategy;
import com.sydata.framework.databind.handle.value.bind.IValueStrategy;
import com.sydata.framework.databind.utils.DataBindUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 分组组合值处理
 * </p>
 * "stockSourceTypeDictValue": "customer,tenant",
 * "stockSourceId": "10564,366",
 * customer对应10564；tenant对应366
 *
 * @author zhoucl
 * @date 2021/5/25 17:19
 */
@Service
public class GroupCommaSeparatedValueBindStrategy implements IValueStrategy<DataBindFieldGroupConfig> {


    @Override
    public String strategy() {
        return DataBindFieldGroupConfig.class.getName();
    }

    @Override
    public void bindQueryKey(DataBindFieldGroupConfig dataBindFieldGroupConfig,
                             Class<? extends Annotation> dataBindStrategy,
                             Map<Class<? extends Annotation>, HashSet<DataBindQuery>> dataBindStrategyDataIdsMap,
                             Map<Class<? extends Annotation>, IDataBindStrategy> dataBindStrategyMap,
                             Field bindField,
                             DataBindSourceData sourceData) {

        this.groupHandle(dataBindFieldGroupConfig, sourceData, (dataBindFieldGroupItemConfig, dataBindStrategy1, dataBindQuery) -> {
            // 直接插入到ids即可
            Set<DataBindQuery> dataBindQuerys = dataBindStrategyDataIdsMap.computeIfAbsent(dataBindStrategy1, k -> new HashSet<>());

            dataBindQuerys.add(dataBindQuery);
        });

    }

    @Override
    public void buildValue(DataBindFieldGroupConfig dataBindFieldGroupConfig,
                           Class<? extends Annotation> dataBindStrategy,
                           Field bindField,
                           Map<Class<? extends Annotation>, Map<?, ?>> dataBindStrategyDataMap,
                           Map<Class<? extends Annotation>, IDataBindStrategy> dataBindStrategyMap,
                           DataBindSourceData sourceData) {

        // 数据拼装
        StringJoiner buildValue = new StringJoiner(DataBindConstants.STRING_DELIMITER);

        this.groupHandle(dataBindFieldGroupConfig, sourceData, (dataBindFieldGroupItemConfig, dataBindStrategy1, dataBindQuery) -> {
            // 通过id拿到远端数据
            Object strategyData = dataBindStrategyDataMap.get(dataBindStrategy1).get(dataBindQuery);
            DataBindUtils.convertStrategyData(strategyData, (data, unKey) -> {
                if (Objects.nonNull(data)) {
                    // 取到远端字段数据
                    Object fieldValue = DataBindHandleBootstrap.getFieldValue(data, unKey, dataBindFieldGroupItemConfig.dataValue());
                    // 加入到拼装
                    if (Objects.nonNull(fieldValue)) {
                        buildValue.add(fieldValue.toString());
                    }
                }
            });
        });

        // 有数据赋值
        if (buildValue.length() > 0) {
            DataBindHandleBootstrap.setValueField(sourceData.getData(), sourceData.getUnKey(), buildValue.toString(), bindField);
        }
    }

    /**
     * 分组处理回调
     *
     * @author zhoucl
     */
    interface GroupHandleCallback {

        /**
         * 处理回调
         *
         * @param dataBindFieldGroupItemConfig 本次分组明细配置注解
         * @param dataBindStrategy             本次明细查询方案
         * @param dataBindQuery                查询id
         */
        void callback(DataBindFieldGroupItemConfig dataBindFieldGroupItemConfig,
                      Class<? extends Annotation> dataBindStrategy,
                      DataBindQuery dataBindQuery);
    }

    /**
     * 统一基本处理
     *
     * @param dataBindFieldGroupConfig 分组配置注解
     * @param sourceData               本次处理对象
     * @param callback                 回调
     */
    private void groupHandle(DataBindFieldGroupConfig dataBindFieldGroupConfig,
                             DataBindSourceData sourceData,
                             GroupHandleCallback callback) {
        // "stockSourceTypeDictValue": "customer,tenant",
        // "stockSourceId": "10564,366",
        // 获取type字段跟value字段值
        Object sourceTypeField = DataBindHandleBootstrap.getFieldValue(sourceData.getData(), sourceData.getUnKey(), dataBindFieldGroupConfig.sourceTypeField());
        Object sourceValueField = DataBindHandleBootstrap.getFieldValue(sourceData.getData(), sourceData.getUnKey(), dataBindFieldGroupConfig.sourceValueField());
        if (Objects.nonNull(sourceTypeField) && Objects.nonNull(sourceValueField)) {
            // 分别按照逗号拆开
            String[] types = StringUtils.split(sourceTypeField.toString(), DataBindConstants.STRING_DELIMITER);
            String[] values = StringUtils.split(sourceValueField.toString(), DataBindConstants.STRING_DELIMITER);
            // 长度不一样不做处理
            if (ArrayUtils.isNotEmpty(types) && ArrayUtils.isNotEmpty(values) && types.length == values.length) {

                //配置的明细方案转成Map
                Map<String, DataBindFieldGroupItemConfig> dataBindFieldGroupItemConfigMap =
                        Arrays.stream(dataBindFieldGroupConfig.item())
                                .collect(Collectors.toMap(DataBindFieldGroupItemConfig::dataType, t -> t));

                // 解析到的数据方案
                Class<? extends Annotation> dataBindStrategy;
                // 查询id
                DataBindQuery dataBindQuery;

                for (int i = 0; i < types.length; i++) {
                    // 类型对应方案配置注解
                    DataBindFieldGroupItemConfig dataBindFieldGroupItemConfig = dataBindFieldGroupItemConfigMap.get(types[i]);

                    if (dataBindFieldGroupItemConfig != null) {
                        // 真实的查询方案
                        dataBindStrategy = dataBindFieldGroupItemConfig.dataBindStrategy();

                        // 构建查询id
                        dataBindQuery = DataBindUtils.buildDataBindQuery(
                                dataBindFieldGroupItemConfig.queryColumn(),
                                DataBindUtils.parseExpressionQueryAndSql(
                                        dataBindFieldGroupItemConfig.queryAndSql(),
                                        dataBindFieldGroupItemConfig.queryAndSqlColumns(),
                                        sourceData.getData()),
                                values[i],
                                dataBindFieldGroupItemConfig.sourceFieldCombination());
                        // 交给回调处理
                        callback.callback(dataBindFieldGroupItemConfig, dataBindStrategy, dataBindQuery);
                    }
                }
            }
        }
    }
}
