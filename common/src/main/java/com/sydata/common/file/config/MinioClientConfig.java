package com.sydata.common.file.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author lzq
 * @describe minio 客户端配置
 * @date 2022-06-23 18:38
 */
@Configuration
public class MinioClientConfig {

    @Resource
    private MinioConfig minioConfig;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder().endpoint(minioConfig.getUrl())
                .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                .build();
    }
}
