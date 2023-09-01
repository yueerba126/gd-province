package com.sydata.organize.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author lzq
 * @describe app应用登录参数
 * @date 2022-06-29 19:44
 */
@Data
public class AppLoginParam implements Serializable {

    @NotBlank(message = "appid/appkey必须填写")
    @Size(min = 16, max = 16, message = "appid/appkey错误")
    @ApiModelProperty(value = "应用ID")
    private String appid;

    @NotBlank(message = "appid/appkey必须填写")
    @JsonProperty("appkey")
    @ApiModelProperty(value = "appKey")
    private String appKey;
}
