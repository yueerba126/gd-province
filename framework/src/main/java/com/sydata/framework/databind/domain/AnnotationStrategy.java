package com.sydata.framework.databind.domain;

import com.sydata.framework.databind.handle.strategy.IDataBindStrategy;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.lang.annotation.Annotation;

/**
 * 注解对应方案
 *
 * @author zhoucl
 * @date 2021/7/13 9:48
 */
@Data
@Accessors(chain = true)
public class AnnotationStrategy implements Serializable {

    /**
     * 数据绑定注解
     */
    private Class<? extends Annotation> annotation;

    /**
     * 注解对应实现方案
     */
    private IDataBindStrategy dataBindStrategy;
}
