package com.sydata.safe.asess.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author lzq
 * @description 部门考核分配参数
 * @date 2023/2/20 15:04
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "部门考核分配参数")
public class OrgAssessDeptAllotParam implements Serializable {

    @NotBlank(message = "部门考核ID不能为空")
    @ApiModelProperty(value = "部门考核ID")
    private String id;

    @NotBlank(message = "状态不能为空")
    @ApiModelProperty(value = "状态:待分配、已分配")
    private String state;

    @Valid
    @NotEmpty(message = "部门考核指标分配参数列表不能为空")
    @ApiModelProperty(value = "部门考核指标分配参数列表")
    private List<OrgAssessDeptIndexDistributionParam> list;
}
