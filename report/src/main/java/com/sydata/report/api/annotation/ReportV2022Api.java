package com.sydata.report.api.annotation;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.InterceptMark;
import com.sydata.report.api.interceptor.ReportV2022Interceptor;

import java.lang.annotation.*;

/**
 * @author lzq
 * @describe 2022上报API拦截标记注解
 * @date 2022-10-31 16:33
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@InterceptMark
public @interface ReportV2022Api {

    String[] include() default {"/**"};

    String[] exclude() default {};

    Class<? extends BasePathMatchInterceptor> handler() default ReportV2022Interceptor.class;
}