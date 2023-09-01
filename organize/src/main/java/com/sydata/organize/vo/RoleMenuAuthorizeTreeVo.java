package com.sydata.organize.vo;

import com.sydata.organize.domain.Menu;
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
 * @describe 角色菜单授权树VO
 * @date 2022-07-01 21:40
 */
@ApiModel(description = "角色菜单授权树VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RoleMenuAuthorizeTreeVo extends Menu implements Serializable {

    @ApiModelProperty(value = "子节点")
    private List<RoleMenuAuthorizeTreeVo> child;

    @ApiModelProperty(value = "是否选中")
    private boolean checked;
}
