package com.sydata.framework.databind.service;

import java.util.Map;

/**
 * 数据绑定调用前参数设置
 *
 * @author zhoucl
 * @date 2021/11/17 9:39
 */
public interface DataBindParamInterceptor {

    /**
     * 数据绑定请求之前参数设置
     *
     * @param cacheMap 指定已换成参数
     */
    void apply(Map<String, Object> cacheMap);

    /**
     * 获取需要传递给后续子线程的数据对象
     *
     * @return 是否使用该拦截器
     */
    Object getTransmitObject();

    /**
     * 传递上一步获取的对象到子线程
     *
     * @param object 传递数据对象
     */
    void setTransmitObjectToThread(Object object);

    /**
     * 清除掉传递到子线程的对象
     */
    void resetTransmitObject();
}
