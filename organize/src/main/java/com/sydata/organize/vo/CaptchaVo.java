package com.sydata.organize.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @describe 验证码VO
 * @date 2022-08-19
 */
@Data
@ApiModel(description = "验证码VO")
@Accessors(chain = true)
public class CaptchaVo implements Serializable {

    @ApiModelProperty(value = "Base64的DataURI形式图片")
    private String code;

    @ApiModelProperty(value = "验证码ID")
    private String verifyId;
}
