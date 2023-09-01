package com.sydata.organize.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author lzq
 * @describe 用户删除参数
 * @date 2022-06-30 17:07
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "用户删除参数")
public class UserRemoveParam implements Serializable {

    @NotBlank(message = "ID不能为空")
    @ApiModelProperty(value = "ID")
    private String id;

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty(value = "账号")
    private String account;
}
