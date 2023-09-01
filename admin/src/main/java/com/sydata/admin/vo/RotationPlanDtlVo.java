package com.sydata.admin.vo;

import com.sydata.admin.domain.RotationPlanDtl;
import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 轮换计划明细
 * </p>
 *
 * @author lzq
 * @since 2022-05-06
 */
@ApiModel(description = "行政管理-轮换计划明细VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RotationPlanDtlVo extends RotationPlanDtl implements Serializable {

    @ApiModelProperty(value = "轮换类型名称")
    @DataBindDict(sourceField = "#lhlx", sourceFieldCombination = "lhlx")
    private String lhlxName;

    @DataBindDict(sourceFieldCombination = "food_variety", sourceField = "#lspzdm")
    @ApiModelProperty(value = "粮食品种名称")
    private String lspzdmName;

    @DataBindDict(sourceFieldCombination = "food_grade", sourceField = "#lsdjdm")
    @ApiModelProperty(value = "粮食等级名称")
    private String lsdjdmName;

    @DataBindDict(sourceFieldCombination = "food_nature", sourceField = "#lsxzdm")
    @ApiModelProperty(value = "粮食性质名称")
    private String lsxzdmName;
}
