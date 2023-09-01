
package com.sydata.organize.vo;

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
 * @describe 用户的角色授权VO
 * @date 2022-07-01 21:40
 */
@ApiModel(description = "用户的角色授权VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserRoleAuthorizeRoleVo extends Role implements Serializable {

    @ApiModelProperty(value = "是否选中")
    private boolean checked;
}
