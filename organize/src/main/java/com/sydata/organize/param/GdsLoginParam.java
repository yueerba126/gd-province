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
 * @describe 广东省统一身份认证登录参数
 * @date 2022-06-29 09:16
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "广东省统一身份认证登录参数")
public class GdsLoginParam implements Serializable {

    @NotBlank(message = "授权码不能为空")
    @ApiModelProperty(value = "授权码")
    private String code;

    @NotBlank(message = "回调地址不能为空")
    @ApiModelProperty(value = "回调地址")
    private String redirectUri;

    @NotBlank(message = "登录设备错误")
    @ApiModelProperty(value = "登录设备", example = "pc")
    private String device;
}
