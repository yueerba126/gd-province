package com.sydata.organize.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lzq
 * @description app应用登录响应VO
 * @date 2022/10/21 18:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppLoginVo implements Serializable {

    @ApiModelProperty(value = "应用ID")
    private String appid;

    @JsonProperty("access_token")
    @ApiModelProperty(value = "访问token")
    private String accessToken;

    @JsonProperty("dwdm")
    @ApiModelProperty(value = "企业信用代码")
    private String organizeId;

    @JsonProperty("dwmc")
    @ApiModelProperty(value = "企业名称")
    private String organizeName;
}
