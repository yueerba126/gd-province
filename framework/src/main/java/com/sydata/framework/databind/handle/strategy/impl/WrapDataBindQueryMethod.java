package com.sydata.framework.databind.handle.strategy.impl;

import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.domain.DataBindSourceData;
import com.sydata.framework.databind.handle.strategy.IDataBindStrategy;
import lombok.AllArgsConstructor;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 包裹查询方法
 *
 * @author zhoucl
 * @date 2022年05月13日 9:35
 */
@AllArgsConstructor
public class WrapDataBindQueryMethod implements IDataBindStrategy {

    private Object bean;

    private Method method;

    @Override
    public Map<?, ?> queryData(Collection<DataBindQuery> dataBindQuerys) {
        try {
            Map<Object, Object> dataMap = new HashMap<>(dataBindQuerys.size());
            if (CollectionUtils.isEmpty(dataBindQuerys)) {
                return dataMap;
            }
            // 反射调用查询方法
            method.invoke(bean, dataBindQuerys);

            // 剩下没有获取到缓存的ids做远端查询
            if (!dataBindQuerys.isEmpty()) {
                StreamEx.of(CollectionUtils.emptyIfNull(dataBindQuerys))
                        .forEach(dataBindQuery -> {
                            Object data = dataBindQuery.getData();
                            dataBindQuery.setData(null);
                            dataMap.put(dataBindQuery, this.buildDataBindSourceData(data));
                        });
            }
            return dataMap;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("数据绑定查询服务【查询】出现异常:" + e.getMessage(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("数据绑定查询服务【查询】出现异常:" + e.getTargetException().getMessage(),
                    e.getTargetException());
        }
    }

    private DataBindSourceData buildDataBindSourceData(Object data) {
        return new DataBindSourceData(data, UUID.randomUUID().toString());
    }
}
