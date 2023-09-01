package com.sydata.framework.databind.service.impl;

import com.sydata.framework.databind.annotation.DataBindFieldGroupConfig;
import com.sydata.framework.databind.domain.DataBindItem;
import com.sydata.framework.databind.domain.DataBindObject;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.domain.DataBindSourceData;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.databind.handle.DataBindQueryBootstrap;
import com.sydata.framework.databind.handle.strategy.IDataBindStrategy;
import com.sydata.framework.databind.handle.strategy.IDataQueryService;
import com.sydata.framework.databind.handle.value.bind.ValueBindHandle;
import com.sydata.framework.databind.service.DataBindParamInterceptor;
import com.sydata.framework.databind.service.IDataBindService;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.keyvalue.DefaultKeyValue;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;

import static com.sydata.framework.databind.handle.DataBindQueryBootstrap.DATA_BIND_STRATEGY_MAP;

/**
 * 数据绑定真实处理类
 *
 * @author zhoucl
 * @date 2021/4/21 11:22
 */
@Slf4j
@Service
public class DataBindServiceImpl implements IDataBindService {
    /**
     * 扩展没有对应方案的注解
     */
    private final List<Class<? extends Annotation>> extendStrategyAnnotations = new ArrayList<>();

    public DataBindServiceImpl(List<IDataQueryService> dataQueryServices, List<IDataBindStrategy> dataBindStrategies) {
        DataBindQueryBootstrap.registerDataQueryService(dataQueryServices);
        DataBindQueryBootstrap.registerDataStrategyService(dataBindStrategies);
        extendStrategyAnnotations.add(DataBindFieldGroupConfig.class);
    }

    @Override
    public boolean supportDataBindAnnotation(Class<? extends Annotation> annotation) {
        return extendStrategyAnnotations.contains(annotation) ||
                DATA_BIND_STRATEGY_MAP.containsKey(annotation);
    }

    @Override
    public boolean supportExtendStrategyAnnotations(Class<? extends Annotation> annotation) {
        return extendStrategyAnnotations.contains(annotation);
    }

    @Override
    public void dataBind(Collection<DataBindObject> dataBindObjects, int convertNumber) {
        if (CollectionUtils.isEmpty(dataBindObjects)) {
            return;
        }

        /*
         * 有些情况需要多次绑定结果
         * 比如：绑定对象只有物料id，此时我需要从物料对象中获取出物料类型id，然后在通过物料类型获取物料类型名称
         * 如果只绑定一次值就只能获取到物料类型id而无法绑定物料类型名称
         * 此时就需要多次绑定结果
         * 但是如果超过5次就需要看看是否是调用方设计的不合理了，多数情况不会超过2次
         */
        Assert.state(convertNumber < 5, "超过5次值绑定，请检查设计是否合理。");

        for (int i = 0; i < convertNumber; i++) {
            Map<Class<? extends Annotation>, HashSet<DataBindQuery>> sourceData = parseSourceData(dataBindObjects);
            Map<Class<? extends Annotation>, Map<?, ?>> dataMap = findDataMap(sourceData);
            bindData(dataBindObjects, dataMap);
        }
    }

    /**
     * 解析来源数据
     *
     * @param dataBindObjects 数据绑定
     * @return 找出所有查询维度，维度是key，value是对应数据的ids
     */
    private Map<Class<? extends Annotation>, HashSet<DataBindQuery>> parseSourceData(Collection<DataBindObject> dataBindObjects) {
        // 找出所有查询维度，维度是key，value是对应数据的ids
        Map<Class<? extends Annotation>, HashSet<DataBindQuery>> dataBindStrategyDataIdsMap = new HashMap<>(128);

        // 循环数据取出每个维度对应ids
        for (DataBindObject dataBindObject : dataBindObjects) {
            Collection<DataBindSourceData> datas = dataBindObject.getDatas();
            if (CollectionUtils.isNotEmpty(datas)) {
                List<DataBindItem> items = dataBindObject.getItems();
                for (DataBindSourceData sourceData : datas) {
                    if (Objects.nonNull(sourceData)
                            && Objects.nonNull(sourceData.getData())
                            && CollectionUtils.isNotEmpty(items)) {
                        items.forEach(dataBindItem -> {
                            // 找到key处理方案
                            Annotation annotation = dataBindItem.getDataBindFieldConfig();
                            ValueBindHandle.findValueStrategy(annotation)
                                    .bindQueryKey(annotation,
                                            dataBindItem.getDataBindStrategy(),
                                            dataBindStrategyDataIdsMap,
                                            DATA_BIND_STRATEGY_MAP,
                                            dataBindItem.getBindField(),
                                            sourceData);
                        });
                    }
                }
            }
        }

        return dataBindStrategyDataIdsMap;
    }

    /**
     * 查询数据
     *
     * @param sourceData 来源数据
     * @return 存储key=维度，value=每个维度查询后的结果Map
     */
    private Map<Class<? extends Annotation>, Map<?, ?>> findDataMap(Map<Class<? extends Annotation>, HashSet<DataBindQuery>> sourceData) {

        Optional<DataBindParamInterceptor> paramInterceptorOpt = StreamEx
                .of(DataBindHandleBootstrap.dataBindParamInterceptors)
                .findFirst(item -> Objects.nonNull(item.getTransmitObject()));

        Object transmitObject = paramInterceptorOpt.map(DataBindParamInterceptor::getTransmitObject).orElse(null);
        Thread thread = Thread.currentThread();

        // 存储key=维度，value=每个维度查询后的结果Map
        Map<Class<? extends Annotation>, Map<?, ?>> dataBindStrategyDataMap = new ConcurrentHashMap<>(sourceData.size());
        StreamEx.of(sourceData.entrySet())
                .filter(entry -> DATA_BIND_STRATEGY_MAP.containsKey(entry.getKey()))
                .parallel(ForkJoinPool.commonPool())
                .map(entry -> {
                    try {
                        if (thread != Thread.currentThread()) {
                            paramInterceptorOpt.ifPresent((item) -> item.setTransmitObjectToThread(transmitObject));
                        }

                        DefaultKeyValue<Class<? extends Annotation>, Map<?, ?>> keyValue = new DefaultKeyValue<>();
                        keyValue.setKey(entry.getKey());
                        if (CollectionUtils.isEmpty(entry.getValue())) {
                            keyValue.setValue(MapUtils.EMPTY_SORTED_MAP);
                        } else {
                            IDataBindStrategy strategy = DATA_BIND_STRATEGY_MAP.get(entry.getKey());
                            Map<?, ?> value = strategy.queryData(entry.getValue());
                            keyValue.setValue(value);
                        }
                        return keyValue;


                    } finally {
                        if (thread != Thread.currentThread()) {
                            paramInterceptorOpt.ifPresent(DataBindParamInterceptor::resetTransmitObject);
                        }
                    }
                })
                .forEach(keyValue -> dataBindStrategyDataMap.put(keyValue.getKey(), keyValue.getValue()));

        return dataBindStrategyDataMap;
    }

    /**
     * 绑定数据
     *
     * @param dataBindObjects 数据绑定
     * @param dataMap         数据本身
     */
    private void bindData(Collection<DataBindObject> dataBindObjects,
                          Map<Class<? extends Annotation>, Map<?, ?>> dataMap) {
        // 循环处理每组数据
        for (DataBindObject dataBindObject : dataBindObjects) {
            // 待处理的每组数据
            Collection<DataBindSourceData> datas = dataBindObject.getDatas();
            if (CollectionUtils.isNotEmpty(datas)) {
                // 每组数据对应需要赋值的方案
                List<DataBindItem> items = dataBindObject.getItems();
                for (DataBindSourceData sourceData : datas) {
                    if (Objects.nonNull(sourceData)
                            && Objects.nonNull(sourceData.getData())
                            && CollectionUtils.isNotEmpty(items)) {
                        // 处理每个方案
                        items.forEach(dataBindItem -> {
                            // 找到绑定结果的实现类开始绑定结果
                            ValueBindHandle.findValueStrategy(dataBindItem.getDataBindFieldConfig())
                                    .buildValue(dataBindItem.getDataBindFieldConfig(),
                                            dataBindItem.getDataBindStrategy(),
                                            dataBindItem.getBindField(),
                                            dataMap,
                                            DATA_BIND_STRATEGY_MAP,
                                            sourceData);
                        });
                    }
                }
            }
        }
    }
}
