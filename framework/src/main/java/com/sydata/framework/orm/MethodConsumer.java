package com.sydata.framework.orm;


import cn.hutool.core.util.ReflectUtil;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.function.Consumer;


/**
 * @author lzq
 * @describe 方法Consumer
 * @date 2022-10-27 14:10
 */
@FunctionalInterface
public interface MethodConsumer<T> extends Consumer<T>, Serializable {

    /**
     * 获取方法名称
     *
     * @return 方法名称
     */
    @SneakyThrows(Throwable.class)
    default String getMethodName() {
        Method method = ReflectUtil.getMethodByName(this.getClass(), "writeReplace");
        method.setAccessible(true);
        SerializedLambda serializedLambda = (SerializedLambda) method.invoke(this);
        return serializedLambda.getImplMethodName();
    }
}