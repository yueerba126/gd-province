package com.sydata.organize.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.framework.databind.config.DataBindConstants;
import com.sydata.organize.annotation.DataBindRole;
import com.sydata.organize.domain.Role;
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
 * @describe 组织架构-用户角色-角色详情VO
 * @date 2022-07-01 20:37
 */
@ApiModel(description = "组织架构-用户角色-角色详情VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserRoleRoleVo implements Serializable {

    @JsonIgnore
    @ApiModelProperty(value = "角色ID", hidden = true)
    private Long roleId;

    @DataBindRole(dataValue = DataBindConstants.EL_DATA_THIS)
    @ApiModelProperty(value = "角色信息")
    private Role role;
}
