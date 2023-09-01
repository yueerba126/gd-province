package com.sydata.report.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lianjiatech.retrofit.spring.boot.core.SourceOkHttpClientRegistrar;
import com.github.lianjiatech.retrofit.spring.boot.core.SourceOkHttpClientRegistry;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author lzq
 * @describe 上报OkHttpClient注册配置
 * @date 2022/10/31 14:33
 */
@Slf4j
@Component
public class ReportOkHttpClientRegistrar implements SourceOkHttpClientRegistrar {

    public final static String REPORT_OK_HTTP_CLIENT = "reportOkHttpClient";

    @Resource
    private ReportConfig reportConfig;

    @Bean
    public JacksonConverterFactory jacksonConverterFactory(ObjectMapper objectMapper) {
        return JacksonConverterFactory.create(objectMapper);
    }

    @Override
    public void register(SourceOkHttpClientRegistry registry) {
        TimeUnit timeoutUnit = reportConfig.getTimeoutUnit();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(reportConfig.getMaxRows(), 5, TimeUnit.MINUTES))
                .callTimeout(reportConfig.getCallTimeout(), timeoutUnit)
                .connectTimeout(reportConfig.getConnectTimeout(), timeoutUnit)
                .readTimeout(reportConfig.getReadTimeout(), timeoutUnit)
                .writeTimeout(reportConfig.getWriteTimeout(), timeoutUnit)
                .build();
        registry.register(REPORT_OK_HTTP_CLIENT, okHttpClient);
    }
}