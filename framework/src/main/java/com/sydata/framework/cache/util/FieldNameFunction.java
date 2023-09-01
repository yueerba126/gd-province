package com.sydata.framework.cache.util;


import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.function.Function;


/**
 * @author lzq
 * @describe 字段名Function
 * @date 2022-04-12 09:29
 */
@FunctionalInterface
public interface FieldNameFunction<T, R> extends Function<T, R>, Serializable {

    String GET = "get";
    String IS = "is";

    /**
     * 获取字段名称
     *
     * @return 字段名称
     */
    @SneakyThrows(Throwable.class)
    default String getFieldName() {
        Method method = ReflectUtil.getMethodByName(this.getClass(), "writeReplace");
        method.setAccessible(true);
        SerializedLambda serializedLambda = (SerializedLambda) method.invoke(this);
        String methodName = serializedLambda.getImplMethodName();

        if (methodName.startsWith(GET)) {
            methodName = methodName.substring(3);
        } else if (methodName.startsWith(IS)) {
            methodName = methodName.substring(2);
        }
        return CharSequenceUtil.lowerFirst(methodName);
    }
}