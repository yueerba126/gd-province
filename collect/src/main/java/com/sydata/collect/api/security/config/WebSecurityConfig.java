package com.sydata.collect.api.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzq
 * @describe WEB安全加密配置
 * @date 2022-04-08 18:05
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "web.security")
public class WebSecurityConfig {

    /**
     * 签名摘要标识符（携带报文头）
     */
    private String digest;

    /**
     * 请求标识,token+时间戳加密（携带报文头）
     */
    private String nonce;

    /**
     * 请求有效期,用于验证请求是否已过期（秒）
     */
    private int requestOvertime;

    /**
     * 请求标识requestId,token+19位雪花ID（携带报文头）
     */
    private String requestId;
}
