package com.sydata.dostrict.reserve.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.dostrict.reserve.domain.ApparitorRotationPlan;
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
 * @date 2022-04-26
 * @description 粮食储备-轮换计划VO
 * @version: 1.0
 */
@ApiModel(description = "粮食储备-轮换计划VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ApparitorRotationPlanVo extends ApparitorRotationPlan implements Serializable {

    @ApiModelProperty(value = "计划下达单位名称")
    @DataBindCompany(sourceField = "#jhxddw")
    private String jhxddwName;
}
