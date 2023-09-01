package com.sydata.common.file.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * Minio 配置信息
 *
 * @author ruoiy
 */
@Configuration
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioConfig {

    @ApiModelProperty(value = "服务地址")
    private String url;

    @ApiModelProperty(value = "用户名")
    private String accessKey;

    @ApiModelProperty(value = "密码")
    private String secretKey;

    @ApiModelProperty(value = "存储桶名称")
    private String bucketName;
}
