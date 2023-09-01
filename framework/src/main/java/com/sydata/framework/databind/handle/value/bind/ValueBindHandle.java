package com.sydata.framework.databind.handle.value.bind;

import com.sydata.framework.databind.annotation.DataBindFieldConfig;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * 值绑定方案工具类
 *
 * @author zhoucl
 * @date 2021/5/25 19:45
 */
@Slf4j
@Component
public class ValueBindHandle {

    /**
     * 绑定值方案实现类
     */
    private static Map<String, IValueStrategy> VALUE_BIND_STRATEGY_MAP;

    public ValueBindHandle(List<IValueStrategy> valueStrategys) {
        ValueBindHandle.VALUE_BIND_STRATEGY_MAP = StreamEx.of(valueStrategys)
                .toMap(IValueStrategy::strategy, valueStrategy -> valueStrategy);
    }

    /**
     * 通过注解找到奥方案实现类类
     *
     * @param annotation 注解
     * @return 对应方案实现类
     */
    public static IValueStrategy findValueStrategy(Annotation annotation) {
        return VALUE_BIND_STRATEGY_MAP.get(getValueStrategyKey(annotation));
    }

    /**
     * 生成方案实现类唯一key
     *
     * @param annotation
     * @return
     */
    public static String getValueStrategyKey(Annotation annotation) {
        // DataBindFieldConfig直接使用配置的枚举
        if (annotation.annotationType() == DataBindFieldConfig.class) {
            return ((DataBindFieldConfig) annotation).valueBindStrategy().name();
        } else {
            // 其他方案使用注解本身
            return annotation.annotationType().getName();
        }
    }
}
