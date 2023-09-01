package com.sydata.video.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzq
 * @description 海康视频开发API对接
 * @date 2022/12/20 18:05
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "hk")
public class HkVideoConfig {

    @ApiModelProperty(value = "对接地址")
    private String host;

    @ApiModelProperty(value = "协议")
    private String protocol;

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "应用key")
    private String appKey;

    @ApiModelProperty(value = "应用秘钥")
    private String appSecret;

    @ApiModelProperty(value = "根ID")
    private String rootId;

    @ApiModelProperty(value = "用户账号")
    private String userAccount;
}
