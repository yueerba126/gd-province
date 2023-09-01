package com.sydata.report.api.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author lzq
 * @description 上报国家平台配置文件
 * @date 2022/10/31 14:33
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "report")
public class ReportConfig {

    @ApiModelProperty(value = "所属省份区域ID")
    private String provinceId;

    @ApiModelProperty(value = "所属省份名称")
    private String provinceName;

    @ApiModelProperty(value = "私钥")
    private String privateKey;

    @ApiModelProperty(value = "公钥")
    private String publicKey;

    @ApiModelProperty(value = "国家平台公钥")
    private String countryPublicKey;

    @ApiModelProperty(value = "上报地址")
    private String baseUrl;

    @ApiModelProperty(value = "最大传输行数")
    private int maxRows;

    @ApiModelProperty(value = "处理线程数")
    private int processThreads;

    @ApiModelProperty(value = "最大重报投递数")
    private int maxDelivery;

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
}
