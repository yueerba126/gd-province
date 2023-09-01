package com.sydata.organize.config;

import com.github.lianjiatech.retrofit.spring.boot.core.SourceOkHttpClientRegistrar;
import com.github.lianjiatech.retrofit.spring.boot.core.SourceOkHttpClientRegistry;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author lzq
 * @description 广东省统一身份认证配置
 * @date 2023/5/23 10:27
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "gds-unified-identity")
public class GdsUnifiedIdentityConfig implements SourceOkHttpClientRegistrar {

    public final static String GDS_UNIFIED_IDENTITY_OK_HTTP_CLIENT = "gdsUnifiedIdentityOkHttpClient";

    @ApiModelProperty(value = "域名地址")
    private String baseUrl;

    @ApiModelProperty(value = "应用标识")
    private String clientId;

    @ApiModelProperty(value = "应用秘钥")
    private String clientSecret;

    @ApiModelProperty(value = "请求服务端返回的类型")
    private String grantType;

    @ApiModelProperty(value = "请求服务端返回的类型")
    private String responseType;

    @ApiModelProperty(value = "应用授权作用域")
    private String scope;

    @ApiModelProperty(value = "超时时间单位")
    private TimeUnit timeoutUnit;

    @ApiModelProperty(value = "通讯超时时间")
    private int callTimeout;

    @ApiModelProperty(value = "连接超时时间")
    private int connectTimeout;

    @ApiModelProperty(value = "读取超时时间")
    private int readTimeout;

    @ApiModelProperty(value = "写出超时时间")
    private int writeTimeout;

    @Override
    public void register(SourceOkHttpClientRegistry registry) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES))
                .callTimeout(getCallTimeout(), timeoutUnit)
                .connectTimeout(getConnectTimeout(), timeoutUnit)
                .readTimeout(getReadTimeout(), timeoutUnit)
                .writeTimeout(getWriteTimeout(), timeoutUnit)
                .build();
        registry.register(GDS_UNIFIED_IDENTITY_OK_HTTP_CLIENT, okHttpClient);
    }
}
