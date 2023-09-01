package com.sydata.organize.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzq
 * @describe 用户消息配置
 * @date 2023-03-03 19:39
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "user.message")
public class UserMessageConfig {

    @ApiModelProperty(value = "未读消息队列超时时间(分)")
    private int unreadQueueOvertime;

    @ApiModelProperty(value = "未读消息队列最大数")
    private int unreadQueueMaxCount;
}
