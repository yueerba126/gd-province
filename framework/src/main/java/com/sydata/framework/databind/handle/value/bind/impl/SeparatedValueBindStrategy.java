package com.sydata.framework.databind.handle.value.bind.impl;

import com.sydata.framework.databind.annotation.DataBindFieldConfig;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.domain.DataBindSourceData;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.databind.handle.value.bind.ValueBindStrategy;
import com.sydata.framework.databind.utils.DataBindUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 逗号分隔类型id，比如源id字段值（1,2,3,4）
 * #号分隔类型id，比如源id字段值（1#2#3#4）
 *
 * @author zhoucl
 * @date 2021/5/25 11:10
 */
@Service
public class SeparatedValueBindStrategy extends AbstractDataBindFieldConfigValueBindStrategy {
    @Override
    public String strategy() {
        return ValueBindStrategy.SEPARATED.name();
    }

    @Override
    public void doBindQueryKey(DataBindFieldConfig dataBindFieldConfig,
                               Set<DataBindQuery> dataBindQuerys,
                               String queryAndSql,
                               Field bindField,
                               DataBindSourceData sourceData,
                               Object sourceFieldValue) {
        if (Objects.nonNull(sourceFieldValue) && Objects.isNull(getBindFieldValue(bindField, sourceData))) {
            // 按逗号拆开加入到id列表
            String[] keys = StringUtils.split(sourceFieldValue.toString(), dataBindFieldConfig.bindSeparated());
            if (ArrayUtils.isNotEmpty(keys)) {
                List<DataBindQuery> dataBindQueries = Arrays.stream(keys)
                        .map(key -> DataBindUtils.buildDataBindQuery(dataBindFieldConfig, queryAndSql, key))
                        .distinct()
                        .collect(Collectors.toList());
                dataBindQuerys.addAll(dataBindQueries);
            }
        }
    }

    @Override
    public Object getBuildValue(DataBindFieldConfig dataBindFieldConfig,
                                Map<?, ?> datasMap,
                                Object sourceFieldValue,
                                String queryAndSql) {
        if (Objects.nonNull(sourceFieldValue)) {
            String[] keys = StringUtils.split(sourceFieldValue.toString(), dataBindFieldConfig.bindSeparated());
            if (ArrayUtils.isNotEmpty(keys)) {
                // 去重转为list保证获取值的顺序
                List<String> keyList = Arrays.stream(keys).distinct().collect(Collectors.toList());

                StringJoiner sj = new StringJoiner(dataBindFieldConfig.bindSeparated());
                keyList.forEach(key -> {
                    // 通过id获取远端数据
                    Object strategyData = datasMap.get(DataBindUtils.buildDataBindQuery(dataBindFieldConfig, queryAndSql, key));
                    DataBindUtils.convertStrategyData(strategyData, (data, unKey) -> {
                        if (Objects.nonNull(data)) {
                            // 取到方案需要取的远端字段信息依次加入拼接对象
                            Object fieldValue = DataBindHandleBootstrap.getFieldValue(data, unKey, dataBindFieldConfig.dataValue());
                            if (Objects.nonNull(fieldValue)) {
                                sj.add(fieldValue.toString());
                            }
                        }
                    });
                });
                return sj.toString();
            }
        }
        return null;
    }
}
