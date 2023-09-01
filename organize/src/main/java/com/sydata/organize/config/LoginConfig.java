package com.sydata.organize.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzq
 * @describe 登录密码配置
 * @date 2022-06-29 09:58
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "login")
public class LoginConfig {

    @ApiModelProperty(value = "加密私钥")
    private String privateKey;

    @ApiModelProperty(value = "加密公钥")
    private String publicKey;

    @ApiModelProperty(value = "验证码缓存key")
    private String captchaCacheKey;

    @ApiModelProperty(value = "验证码有效期(秒)")
    private int captchaValidTime;

    @ApiModelProperty(value = "验证码缓存时间(秒)")
    private int captchaCacheTime;

    @ApiModelProperty(value = "密码最小长度")
    private int passwordMinLength;

    @ApiModelProperty(value = "密码最大长度")
    private int passwordMaxLength;

    @ApiModelProperty(value = "登录失败缓存key")
    private String loginFailCacheKey;

    @ApiModelProperty(value = "登录失败缓存时间(秒)")
    private int loginFailCacheTime;

    @ApiModelProperty(value = "登录失败触发封禁次数")
    private int loginFailOnDisableCount;

    @ApiModelProperty(value = "封禁时间(秒)")
    private int disableTime;

    @ApiModelProperty(value = "免认证路径集合")
    private List<String> ignorePaths = new ArrayList<>();
}
