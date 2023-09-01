package com.sydata.collect.api.security.config;

import com.sydata.collect.api.security.interceptor.RequestReplayInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author lzq
 * @describe WebMvc安全拦截器配置
 * @date 2022-06-29 09:58
 */
@Configuration
public class WebMvcSecurityConfigure implements WebMvcConfigurer {

    @Resource
    private RequestReplayInterceptor requestReplayInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestReplayInterceptor).addPathPatterns("/**");
    }
}
