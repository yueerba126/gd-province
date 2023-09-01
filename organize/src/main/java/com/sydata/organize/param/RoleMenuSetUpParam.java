
package com.sydata.organize.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * @author lzq
 * @describe 角色菜单设置参数
 * @date 2022-07-01 19:01
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "角色菜单设置参数")
public class RoleMenuSetUpParam implements Serializable {

    @NotEmpty(message = "角色ID列表不能为空")
    @ApiModelProperty(value = "角色ID列表")
    private Set<Long> roleIds;

    @NotEmpty(message = "菜单ID列表不能为空")
    @ApiModelProperty(value = "菜单ID列表")
    private Set<Long> menuIds;
}
