package com.sydata.http;

import com.github.lianjiatech.retrofit.spring.boot.core.SourceOkHttpClientRegistrar;
import com.github.lianjiatech.retrofit.spring.boot.core.SourceOkHttpClientRegistry;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author lzq
 * @description 库软件OkHttpClientRegistrar
 * @date 2023/5/23 10:27
 */
@Component
public class KuOkHttpClientConfig implements SourceOkHttpClientRegistrar {

    public final static String KU_OK_HTTP_CLIENT = "kuOkHttpClient";

    @Override
    public void register(SourceOkHttpClientRegistry registry) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(100, 5, TimeUnit.MINUTES))
                .callTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        registry.register(KU_OK_HTTP_CLIENT, okHttpClient);
    }
}
