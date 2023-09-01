package com.sydata.organize.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @description 广东省统一身份认证登录VO
 * @date 2023/5/23 15:29
 */
@ApiModel(description = "广东省统一身份认证登录VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GdsUnifiedIdentityLoginVo implements Serializable {

    @ApiModelProperty(value = "广东省统一身份认证用户ID")
    private String gdsUnifiedIdentityUserId;

    @ApiModelProperty(value = "登录VO")
    private LoginVo loginVo;
}
