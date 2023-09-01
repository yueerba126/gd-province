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
 * @describe 用户修改密码参数
 * @date 2022-06-30 18:10
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "用户修改密码参数")
public class UserUpdatePasswordParam implements Serializable {

    @NotBlank(message = "ID不能为空")
    @ApiModelProperty(value = "ID")
    private String id;

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty(value = "账号")
    private String account;

    @NotBlank(message = "密码不能为空")
    @Size(min = 172, max = 172, message = "密码格式不正确")
    @ApiModelProperty(value = "密码")
    private String password;
}
