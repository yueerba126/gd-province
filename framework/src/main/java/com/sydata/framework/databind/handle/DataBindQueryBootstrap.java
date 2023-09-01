package com.sydata.framework.databind.handle;

import com.baomidou.mybatisplus.extension.toolkit.AopUtils;
import com.sydata.framework.databind.annotation.DataBindFieldConfig;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.AnnotationStrategy;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.handle.strategy.IDataBindStrategy;
import com.sydata.framework.databind.handle.strategy.IDataQueryService;
import com.sydata.framework.databind.handle.strategy.impl.WrapDataBindQueryMethod;
import com.sydata.framework.databind.handle.strategy.impl.WrapDataBindQueryService;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * 数据绑定查询服务加载类
 *
 * @author zhoucl
 * @date 2022年05月13日 9:17
 */
public final class DataBindQueryBootstrap {

    /**
     * 每个方案注解对象的方案实现类
     */
    public static final Map<Class<? extends Annotation>, IDataBindStrategy> DATA_BIND_STRATEGY_MAP = new HashMap<>();

    /**
     * 注册查询服务
     *
     * @param dataBindStrategies 查询服务实现类
     */
    public static void registerDataStrategyService(List<IDataBindStrategy> dataBindStrategies) {
        if (CollectionUtils.isNotEmpty(dataBindStrategies)) {
            DATA_BIND_STRATEGY_MAP.putAll(
                    StreamEx.of(dataBindStrategies)
                            // 过滤没有注解的方案
                            .flatMap(dataBindStrategy -> {
                                DataBindService dataBindService = AnnotationUtils.getAnnotation(AopUtils.getTargetObject(dataBindStrategy).getClass(), DataBindService.class);
                                if (dataBindService == null) {
                                    return StreamEx.empty();
                                }
                                return Arrays.stream(dataBindService.strategy())
                                        .map(annotation -> {
                                            // 校验方案提供者支持的注解是否实现了我们要求的注解
                                            DataBindFieldConfig dataBindFieldConfig = AnnotationUtils.getAnnotation(annotation, DataBindFieldConfig.class);
                                            Assert.notNull(dataBindFieldConfig,
                                                    String.format("%s方案提供的支持的注解没有实现@DataBindFieldConfig注解",
                                                            dataBindStrategy.getClass().getName()));
                                            return new AnnotationStrategy()
                                                    .setAnnotation(annotation)
                                                    .setDataBindStrategy(dataBindStrategy);
                                        });
                            })
                            .toMap(AnnotationStrategy::getAnnotation, AnnotationStrategy::getDataBindStrategy)
            );
        }
    }

    /**
     * 注册查询服务
     *
     * @param dataQueryServices 查询服务实现类
     */
    public static void registerDataQueryService(List<IDataQueryService> dataQueryServices) {
        if (CollectionUtils.isNotEmpty(dataQueryServices)) {
            DATA_BIND_STRATEGY_MAP.putAll(
                    StreamEx.of(dataQueryServices)
                            // 过滤没有注解的方案
                            .flatMap(dataQueryService -> {
                                DataBindService dataBindService = AnnotationUtils.getAnnotation(AopUtils.getTargetObject(dataQueryService).getClass(), DataBindService.class);
                                if (dataBindService == null) {
                                    return StreamEx.empty();
                                }
                                IDataBindStrategy dataBindStrategy = new WrapDataBindQueryService(dataQueryService);
                                return Arrays.stream(dataBindService.strategy())
                                        .map(annotation -> {
                                            // 校验方案提供者支持的注解是否实现了我们要求的注解
                                            DataBindFieldConfig dataBindFieldConfig = AnnotationUtils.getAnnotation(annotation, DataBindFieldConfig.class);
                                            Assert.notNull(dataBindFieldConfig,
                                                    String.format("%s方案提供的支持的注解没有实现@DataBindFieldConfig注解",
                                                            dataQueryService.getClass().getName()));
                                            return new AnnotationStrategy()
                                                    .setAnnotation(annotation)
                                                    .setDataBindStrategy(dataBindStrategy);
                                        });
                            })
                            .toMap(AnnotationStrategy::getAnnotation, AnnotationStrategy::getDataBindStrategy)
            );
        }
    }

    /**
     * 通过Bean方法注册查询服务
     *
     * @param bean            支持数据绑定查询的实例
     * @param dataBindService 注解配置类
     * @param method          查询方法
     */
    public static void registerDataQueryService(Object bean, DataBindService dataBindService, Method method) {
        // 检验参数
        Parameter[] parameters = method.getParameters();
        Assert.notEmpty(parameters, "请填写好数据绑定查询服务参数 Collection<DataBindQuery> dataBindQuerys：" + method);
        Assert.state(parameters.length == 1, "监听方法只能填写一个参数");
        ParameterizedType parameterizedType = (ParameterizedType) parameters[0].getParameterizedType();
        Assert.state(parameterizedType.getRawType() == Collection.class, "监听参数必须是Collection.class");
        Class clz = ((Class) parameterizedType.getActualTypeArguments()[0]);
        Assert.state(clz == DataBindQuery.class, "集合泛型必须是DataBindQuery.class");
        IDataBindStrategy dataBindStrategy = new WrapDataBindQueryMethod(bean, method);
        DATA_BIND_STRATEGY_MAP.putAll(
                StreamEx.of(dataBindService)
                        .flatMap(it -> Arrays.stream(it.strategy())
                                .map(annotation -> new AnnotationStrategy()
                                        .setAnnotation(annotation)
                                        .setDataBindStrategy(dataBindStrategy)))
                        .toMap(AnnotationStrategy::getAnnotation, AnnotationStrategy::getDataBindStrategy)
        );
    }

}
