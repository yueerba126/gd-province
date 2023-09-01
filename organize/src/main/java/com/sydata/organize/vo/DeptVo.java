package com.sydata.organize.vo;

import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.organize.domain.Dept;
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
 * @description 组织架构-部门VO
 * @date 2022/11/16 16:09
 */
@ApiModel(description = "组织架构-部门VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeptVo extends Dept implements Serializable {

    @DataBindOrganize
    @ApiModelProperty(value = "组织名称")
    private String organizeName;
}
