package com.sydata.organize.vo;

import com.sydata.organize.domain.MenuSystem;
import com.sydata.organize.security.LoginUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author lzq
 * @describe 登录VO
 * @date 2022-06-30 19:49
 */
@ApiModel(description = "登录VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LoginVo implements Serializable {

    @ApiModelProperty(value = "token名称")
    private String tokenName;

    @ApiModelProperty(value = "token前缀")
    private String tokenPrefix;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "菜单系统")
    private MenuSystem menuSystem;

    @ApiModelProperty(value = "登录用户")
    private LoginUser loginUser;
}
