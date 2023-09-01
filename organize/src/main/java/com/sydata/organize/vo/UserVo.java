package com.sydata.organize.vo;

import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.organize.annotation.DataBindDept;
import com.sydata.organize.domain.User;
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
 * @describe 组织架构-用户VO
 * @date 2022-06-30 16:50
 */
@ApiModel(description = "组织架构-用户VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserVo extends User implements Serializable {

    @DataBindOrganize
    @ApiModelProperty(value = "组织名称")
    private String organizeName;

    @DataBindDept
    @ApiModelProperty(value = "部门名称")
    private String deptName;
}
