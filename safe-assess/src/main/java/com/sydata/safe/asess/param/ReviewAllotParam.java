package com.sydata.safe.asess.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author lzq
 * @description 考核评审分配参数
 * @date 2023/4/3 17:03
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "考核评审分配参数")
public class ReviewAllotParam implements Serializable {

    @NotBlank(message = "考核模板ID不能为空")
    @ApiModelProperty(value = "考核模板ID")
    private String templateId;

    @NotNull(message = "部门ID不能为空")
    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @NotBlank(message = "状态不能为空")
    @ApiModelProperty(value = "状态：待分配、待发布")
    private String state;

    @NotEmpty(message = "指标分配参数列表不能为空")
    @ApiModelProperty(value = "指标分配参数列表")
    private List<ReviewIndexAllotParam> list;
}
