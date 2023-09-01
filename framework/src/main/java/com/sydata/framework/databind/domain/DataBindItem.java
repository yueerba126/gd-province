package com.sydata.framework.databind.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 绑定方案
 *
 * @author zhoucl
 * @date 2021/4/21 14:50
 */
@Getter
@Setter
@Accessors(chain = true)
public class DataBindItem {

    /**
     * 方案对象配置
     */
    private Annotation dataBindFieldConfig;

    /**
     * 转换数据类型
     */
    private Class<? extends Annotation> dataBindStrategy;

    /**
     * 绑定字段
     */
    private Field bindField;

}
