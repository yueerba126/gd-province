package com.sydata.framework.core.global.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 排除全局响应
 * </p>
 *
 * @author lzq
 * @date 2021/8/2 10:23
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcludeGlobalResponse {

}
