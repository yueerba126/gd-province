package com.sydata.framework.databind.handle.value.bind.impl;

import com.sydata.framework.databind.annotation.DataBindFieldConfig;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.domain.DataBindSourceData;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.databind.handle.value.bind.ValueBindStrategy;
import com.sydata.framework.databind.utils.DataBindUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 默认值绑定
 *
 * @author zhoucl
 * @date 2021/5/25 10:18
 */
@Service
public class DefaultValueBindStrategy extends AbstractDataBindFieldConfigValueBindStrategy {

    @Override
    public String strategy() {
        return ValueBindStrategy.DEFAULT.name();
    }

    @Override
    void doBindQueryKey(DataBindFieldConfig dataBindFieldConfig,
                        Set<DataBindQuery> dataBindQuerys,
                        String queryAndSql,
                        Field bindField,
                        DataBindSourceData sourceData,
                        Object sourceFieldValue) {
        // 待绑定字段不为空才做查询
        // 默认直接入到id列表
        if (Objects.isNull(getBindFieldValue(bindField, sourceData))) {
            dataBindQuerys.add(DataBindUtils.buildDataBindQuery(dataBindFieldConfig, queryAndSql, sourceFieldValue));
        }
    }

    @Override
    Object getBuildValue(DataBindFieldConfig dataBindFieldConfig,
                         Map<?, ?> datasMap,
                         Object sourceFieldValue,
                         String queryAndSql) {
        // 通过构建的id获取到远端查询数据
        Object strategyData = datasMap.get(DataBindUtils.buildDataBindQuery(dataBindFieldConfig, queryAndSql, sourceFieldValue));
        return DataBindUtils.convertStrategyData(strategyData, (data, unKey) -> {
            if (Objects.nonNull(data)) {
                return DataBindHandleBootstrap.getFieldValue(data, unKey, dataBindFieldConfig.dataValue());
            }
            return null;
        });
    }
}
