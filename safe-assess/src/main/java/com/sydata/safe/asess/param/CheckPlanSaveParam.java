package com.sydata.safe.asess.param;

import com.sydata.safe.asess.domain.CheckPlanDtl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * @author lzq
 * @description 抽查计划新增参数
 * @date 2023/2/27 16:57
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "抽查计划新增参数")
public class CheckPlanSaveParam implements Serializable {

    @NotBlank(message = "计划名称必填")
    @ApiModelProperty(value = "计划名称")
    private String planName;

    @NotBlank(message = "年度必填")
    @ApiModelProperty(value = "年度")
    private String planYear;

    @NotNull(message = "计划开始日期必填")
    @ApiModelProperty(value = "计划开始日期")
    private LocalDate startDate;

    @NotNull(message = "计划结束日期必填")
    @ApiModelProperty(value = "计划结束日期")
    private LocalDate endDate;

    @NotBlank(message = "模板id必填")
    @ApiModelProperty(value = "模板id")
    private String templateId;

    @NotBlank(message = "小组id必填")
    @ApiModelProperty(value = "小组id")
    private String groupId;

    @Valid
    private List<CheckPlanDtl> list = Collections.emptyList();
}
