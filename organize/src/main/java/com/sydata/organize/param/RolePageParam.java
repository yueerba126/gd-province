package com.sydata.organize.param;

import com.sydata.common.param.PageQueryParam;
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
 * @describe 角色分页参数
 * @date 2022-06-30 16:45
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "角色分页参数")
public class RolePageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色标识")
    private String permission;
}
