package com.sydata.framework.databind.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * 数据绑定自定义实现服务
 *
 * @author zhoucl
 */
@Service
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataBindService {

    @AliasFor(annotation = Service.class)
    String value() default "";

    /**
     * 服务支持的注解
     *
     * @return 支持注解列表
     */
    Class<? extends Annotation>[] strategy();
}
