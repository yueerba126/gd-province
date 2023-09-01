package com.sydata.dostrict.reserve.vo;

import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.dostrict.reserve.domain.ApparitorRotationPlanDtl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @date 2022-04-26
 * @description 粮食储备-轮换计划明细VO
 * @version: 1.0
 */
@ApiModel(description = "粮食储备-轮换计划明细VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ApparitorRotationPlanDtlVo extends ApparitorRotationPlanDtl implements Serializable {

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

    @DataBindCargo(sourceField = "#lhhwdm")
    @ApiModelProperty(value = "轮换货位名称")
    private String lhhwdmName;
}
