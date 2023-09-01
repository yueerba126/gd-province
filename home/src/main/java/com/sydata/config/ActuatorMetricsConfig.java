package com.sydata.config;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 端点Metrics配置类
 * </p>
 *
 * @author lzq
 * @date 2022/10/25 15:10
 */
@Configuration
public class ActuatorMetricsConfig {

    @Bean
    public InitializingBean forcePrometheusPostProcessor(BeanPostProcessor meterRegistryPostProcessor, PrometheusMeterRegistry registry) {
        return () -> meterRegistryPostProcessor.postProcessAfterInitialization(registry, "");
    }
}