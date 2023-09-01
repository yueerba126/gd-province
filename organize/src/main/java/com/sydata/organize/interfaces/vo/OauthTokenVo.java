package com.sydata.organize.interfaces.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @description 广东省统一身份认证获取访问令牌VO
 * @date 2023/5/23 11:30
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "广东省统一身份认证获取访问令牌VO")
public class OauthTokenVo implements Serializable {

    @JsonProperty("access_token")
    @ApiModelProperty(value = "访问令牌")
    private String accessToken;

    @JsonProperty("token_type")
    @ApiModelProperty(value = "令牌类型")
    private String tokenType;

    @JsonProperty("expires_in")
    @ApiModelProperty(value = "访问令牌有效时间")
    private String expiresIn;

    @ApiModelProperty(value = "数据范围")
    private String scope;

    @ApiModelProperty(value = "账号唯一标识")
    private String userid;
}
