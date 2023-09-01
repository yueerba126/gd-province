package com.sydata.framework.orm.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 数据分页优化拦截放行注解
 * </p>
 *
 * @author pangbohuan
 * @date 2022/10/21 20:02
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PageOptimizationExclude {
}