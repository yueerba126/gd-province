package com.sydata.safe.asess.vo;

import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.organize.annotation.DataBindDept;
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
 * @description 粮食安全考核-考核模板分配部门VO
 * @date 2023/4/4 15:34
 */
@ApiModel(description = "粮食安全考核-考核模板分配部门VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TemplateAllotDeptVo implements Serializable {

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @DataBindOrganize(sourceField = "#deptId")
    @ApiModelProperty(value = "部门名称")
    private String deptName;
}
