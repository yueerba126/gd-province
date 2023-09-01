package com.sydata.common.com.sydata.organize.permission.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 数据权限拦截放行注解
 * </p>
 *
 * @author pangbohuan
 * @date 2022/10/21 20:02
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DataPermissionExclude {
}