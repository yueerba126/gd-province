package com.sydata.framework.databind.handle.strategy.impl;

import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.domain.DataBindSourceData;
import com.sydata.framework.databind.handle.strategy.IDataBindStrategy;
import com.sydata.framework.databind.handle.strategy.IDataQueryService;
import lombok.AllArgsConstructor;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 先查询缓存在请求接口基类
 *
 * @author zhoucl
 * @date 2021/5/8 14:16
 */
@AllArgsConstructor
public class WrapDataBindQueryService implements IDataBindStrategy {

    private IDataQueryService dataQueryService;

    @Override
    public Map<?, ?> queryData(Collection<DataBindQuery> dataBindQuerys) {
        Map<Object, Object> dataMap = new HashMap<>(dataBindQuerys.size());
        if (CollectionUtils.isEmpty(dataBindQuerys)) {
            return dataMap;
        }
        // 剩下没有获取到缓存的ids做远端查询
        if (!dataBindQuerys.isEmpty()) {
            dataQueryService.listByQueryColumn(dataBindQuerys);
            StreamEx.of(dataBindQuerys)
                    .forEach(dataBindQuery -> {
                        Object data = dataBindQuery.getData();
                        dataBindQuery.setData(null);
                        dataMap.put(dataBindQuery, this.buildDataBindSourceData(data));
                    });
        }
        return dataMap;
    }

    private DataBindSourceData buildDataBindSourceData(Object data) {
        return new DataBindSourceData(data, UUID.randomUUID().toString());
    }
}
