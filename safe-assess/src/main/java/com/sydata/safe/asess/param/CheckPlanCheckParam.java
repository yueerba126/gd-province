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
 * @description 抽查计划抽查参数
 * @date 2023/2/27 17:51
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "抽查计划抽查参数")
public class CheckPlanCheckParam implements Serializable {

    @NotBlank(message = "计划ID不能为空")
    @ApiModelProperty(value = "计划ID")
    private String planId;

    @NotBlank(message = "计划明细ID不能为空")
    @ApiModelProperty(value = "计划明细ID")
    private String dtlId;

    @NotBlank(message = "抽查状态必填")
    @ApiModelProperty(value = "抽查状态: 抽查中、已抽查")
    private String state;

    @Valid
    @NotEmpty(message = "单位考核指标抽查参数列表不能为空")
    private List<CheckPlanIndexCheckParam> list;
}
