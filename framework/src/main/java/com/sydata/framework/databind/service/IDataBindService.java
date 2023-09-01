package com.sydata.framework.databind.service;



import com.sydata.framework.databind.domain.DataBindObject;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * 数据绑定服务
 *
 * @author zhoucl
 * @date 2021/4/21 11:22
 */
public interface IDataBindService {

    /**
     * 数据绑定
     *
     * @param dataBindObjects 数据绑定
     * @param convertNumber   结果绑定次数
     */
    void dataBind(Collection<DataBindObject> dataBindObjects, int convertNumber);

    /**
     * 注解是否支持数据绑定
     *
     * @param annotation 注解
     * @return 注解是否支持数据绑定
     */
    boolean supportDataBindAnnotation(Class<? extends Annotation> annotation);

    /**
     * 是否在没有具体实现方案扩展注解里面
     *
     * @param annotation 注解
     * @return 是否包含
     */
    boolean supportExtendStrategyAnnotations(Class<? extends Annotation> annotation);
}
