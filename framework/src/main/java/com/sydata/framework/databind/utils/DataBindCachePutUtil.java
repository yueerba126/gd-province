package com.sydata.framework.databind.utils;

import com.sydata.framework.databind.service.DataBindParamInterceptor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author lzq
 * @description 数据绑定缓存put操作工具类
 * @date 2023/4/23 17:02
 */
@Component
public class DataBindCachePutUtil implements DataBindParamInterceptor {

    private static final ThreadLocal<Boolean> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 是否允许PUT操作
     *
     * @return 是否允许PUT操作
     */
    public static Boolean get() {
        return THREAD_LOCAL.get();
    }

    /**
     * 不允许PUT操作
     *
     * @param put 允许标识
     */
    public static void set(Boolean put) {
        THREAD_LOCAL.set(put);
    }

    /**
     * 清理状态
     **/
    public static void clear() {
        THREAD_LOCAL.remove();
    }

    @Override
    public void apply(Map<String, Object> cacheMap) {

    }

    @Override
    public Object getTransmitObject() {
        return get();
    }

    @Override
    public void setTransmitObjectToThread(Object object) {
        set((Boolean) object);
    }

    @Override
    public void resetTransmitObject() {
        clear();
    }
}
