package com.sydata.organize.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


/**
 * @author lzq
 * @describe WebToken拦截器配置
 * @date 2022-06-29 09:58
 */
@Configuration
public class WebTokenConfigure implements WebMvcConfigurer {

    @Resource
    private LoginConfig loginConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 路由拦截器
        registry.addInterceptor(new SaRouteInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(loginConfig.getIgnorePaths());


        // 注解拦截器
        registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**");
    }
}
