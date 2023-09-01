package com.sydata.organize.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author lzq
 * @describe 登录参数
 * @date 2022-06-29 09:16
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "登录参数")
public class LoginParam implements Serializable {

    @NotBlank(message = "用户名或密码错误")
    @ApiModelProperty(value = "账号")
    private String account;

    @NotBlank(message = "用户名或密码错误")
    @Size(min = 172, max = 172, message = "密码格式不正确")
    @ApiModelProperty(value = "密码")
    private String password;

    @NotBlank(message = "登录设备错误")
    @ApiModelProperty(value = "登录设备", example = "pc")
    private String device;

    @NotBlank(message = "验证码错误")
    @ApiModelProperty(value = "验证码")
    private String verify;

    @NotBlank(message = "验证码错误")
    @ApiModelProperty(value = "验证码id")
    private String verifyId;

    @ApiModelProperty(value = "广东省统一身份认证用户ID")
    private String gdsUnifiedIdentityUserId;
}
