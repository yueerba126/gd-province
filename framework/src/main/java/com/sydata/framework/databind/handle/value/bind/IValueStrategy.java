package com.sydata.framework.databind.handle.value.bind;

import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.domain.DataBindSourceData;
import com.sydata.framework.databind.handle.strategy.IDataBindStrategy;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;

/**
 * 具体绑定方案
 *
 * @author zhoucl
 * @date 2021/5/25 10:17
 */
public interface IValueStrategy<A extends Annotation> {
    /**
     * 拼装sql提供字段枚举方法名
     */
    String QUERY_AND_SQL_COLUMNS_METHOD_NAME = "getDataBindFields";

    /**
     * 值绑定方案
     *
     * @return 值绑定方案
     */
    String strategy();

    /**
     * 解析源字段值放入查询id列表
     *
     * @param annotation                 本次数据绑定配置注解
     * @param dataBindStrategy           本次数据绑定方案
     * @param dataBindStrategyDataIdsMap 需要到远端查询map
     * @param dataBindStrategyMap        数据绑定方案集合
     * @param bindField                  待绑定字段
     * @param data                       源数据
     */
    void bindQueryKey(A annotation,
                      Class<? extends Annotation> dataBindStrategy,
                      Map<Class<? extends Annotation>, HashSet<DataBindQuery>> dataBindStrategyDataIdsMap,
                      Map<Class<? extends Annotation>, IDataBindStrategy> dataBindStrategyMap,
                      Field bindField,
                      DataBindSourceData data);

    /**
     * 通过源字段值构建出业务方需要字段值
     *
     * @param annotation              本次数据绑定配置注解
     * @param dataBindStrategy        本次数据绑定方案
     * @param bindField               本次数据绑定字段
     * @param dataBindStrategyDataMap 远端查询到的方案数据
     * @param dataBindStrategyMap     数据绑定方案集合
     * @param data                    源数据
     */
    void buildValue(A annotation,
                    Class<? extends Annotation> dataBindStrategy,
                    Field bindField,
                    Map<Class<? extends Annotation>, Map<?, ?>> dataBindStrategyDataMap,
                    Map<Class<? extends Annotation>, IDataBindStrategy> dataBindStrategyMap,
                    DataBindSourceData data);

    /**
     * 获取绑定字段值
     *
     * @param bindField 绑定字段
     * @param data      字段对应对象
     * @return 绑定字段值
     */
    default Object getBindFieldValue(Field bindField,
                                     DataBindSourceData data) {
        try {
            return FieldUtils.readField(bindField, data.getData(), true);
        } catch (IllegalAccessException ignored) {
            return null;
        }
    }
}
