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
import java.time.LocalDate;
import java.util.List;

/**
 * @author lzq
 * @description 粮食安全考核-单位考核分配参数
 * @date 2023/2/18 9:42
 */
@ApiModel(description = "粮食安全考核-单位考核分配参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrgAssessAllotParam implements Serializable {

    @NotBlank(message = "主键ID不能为空")
    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "配合部门填报截止日期")
    private LocalDate cooperateDeptEndDate;

    @NotBlank(message = "状态不能为空")
    @ApiModelProperty(value = "状态：待分配、待自评")
    private String state;

    @Valid
    @NotEmpty(message = "单位考核指标列表不能为空")
    @ApiModelProperty(value = "单位考核指标分配参数列表")
    private List<OrgAssessIndexDistributionParam> list;
}
