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
import java.time.LocalDateTime;

/**
 * @author lzq
 * @describe 用户分页参数
 * @date 2022-06-30 16:45
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "用户分页参数")
public class UserPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "登录账号")
    private String account;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "组织ID")
    private String organizeId;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;
}
