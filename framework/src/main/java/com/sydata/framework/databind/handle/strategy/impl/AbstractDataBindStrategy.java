package com.sydata.framework.databind.handle.strategy.impl;

import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.domain.DataBindSourceData;
import com.sydata.framework.databind.handle.strategy.IDataBindStrategy;
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
public abstract class AbstractDataBindStrategy implements IDataBindStrategy {

    @Override
    public Map<?, ?> queryData(Collection<DataBindQuery> dataBindQuerys) {
        Map<Object, Object> dataMap = new HashMap<>(dataBindQuerys.size());
        if (CollectionUtils.isEmpty(dataBindQuerys)) {
            return dataMap;
        }
        // 剩下没有获取到缓存的ids做远端查询
        if (!dataBindQuerys.isEmpty()) {
            StreamEx.of(CollectionUtils.emptyIfNull(this.listByQueryColumn(dataBindQuerys)))
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

    /**
     * 获取到远端数据自定处理
     *
     * @param dataBindQuery 远端数据
     */
    public void peek(DataBindQuery dataBindQuery) {

    }

    /**
     * 缓存中不存在时需要去服务端拉取数据
     *
     * @param dataBindQuerys 数据ids
     * @return 数据列表
     */
    public abstract Collection<DataBindQuery> listByQueryColumn(Collection<DataBindQuery> dataBindQuerys);

}
