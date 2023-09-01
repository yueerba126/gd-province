package com.sydata.organize.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author lzq
 * @describe 用户设置部门参数
 * @date 2022-07-01 19:01
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "用户设置部门参数")
public class UserSetUpDeptParam implements Serializable {

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @NotEmpty(message = "用户不能为空")
    @ApiModelProperty(value = "用户ID集合")
    private List<String> userIds;

    @NotEmpty(message = "用户账号不能为空")
    @ApiModelProperty(value = "用户账号集合")
    private List<String> accounts;
}
