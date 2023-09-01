package com.sydata.organize.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lzq
 * @description 生成应用秘钥信息
 * @date 2022/10/21 19:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppSecretGenerateVo implements Serializable {

    @ApiModelProperty(value = "应用ID")
    private String appid;

    @ApiModelProperty(value = "应用key")
    private String appKey;

    @ApiModelProperty(value = "应用秘钥")
    private String appSecret;
}
