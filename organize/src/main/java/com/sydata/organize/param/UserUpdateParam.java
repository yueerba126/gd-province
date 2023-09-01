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
 * @describe 用户修改参数
 * @date 2022-06-30 18:10
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "用户修改参数")
public class UserUpdateParam implements Serializable {

    @NotBlank(message = "ID不能为空")
    @ApiModelProperty(value = "主键ID")
    private String id;

    @NotBlank(message = "登录账号不能为空")
    @ApiModelProperty(value = "登录账号")
    private String account;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名")
    private String name;

    @NotBlank(message = "组织不能为空")
    @ApiModelProperty(value = "组织ID")
    private String organizeId;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;
}
