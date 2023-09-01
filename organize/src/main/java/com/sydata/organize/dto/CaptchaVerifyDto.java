package com.sydata.organize.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @description 验证码验证DTO
 * @date 2022/10/12 20:55
 */
@Data
@ApiModel(description = "验证码验证DTO")
@Accessors(chain = true)
public class CaptchaVerifyDto implements Serializable {

    @ApiModelProperty(value = "生成时间")
    private LocalDateTime genTime;

    @ApiModelProperty(value = "验证码")
    private String verify;
}
