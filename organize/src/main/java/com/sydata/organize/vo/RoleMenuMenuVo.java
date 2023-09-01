package com.sydata.organize.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.organize.annotation.DataBindMenu;
import com.sydata.framework.databind.config.DataBindConstants;
import com.sydata.organize.domain.Menu;
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
 * @describe 组织架构-用户角色-菜单详情VO
 * @date 2022-07-01 21:35
 */
@ApiModel(description = "组织架构-角色菜单-菜单详情VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RoleMenuMenuVo implements Serializable {

    @JsonIgnore
    @ApiModelProperty(value = "菜单ID", hidden = true)
    private Long menuId;

    @DataBindMenu(dataValue = DataBindConstants.EL_DATA_THIS)
    @ApiModelProperty(value = "菜单")
    private Menu menu;
}
