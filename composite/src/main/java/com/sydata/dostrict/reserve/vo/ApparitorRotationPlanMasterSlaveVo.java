package com.sydata.dostrict.reserve.vo;

import com.sydata.dostrict.reserve.domain.ApparitorRotationPlan;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author lzq
 * @date 2022-04-26
 * @description 粮食储备-轮换计划-主从表VO
 * @version: 1.0
 */
@ApiModel(description = "粮食储备-轮换计划-主从表VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ApparitorRotationPlanMasterSlaveVo extends ApparitorRotationPlan implements Serializable {

    @ApiModelProperty(value = "轮换计划明细列表VO")
    private List<ApparitorRotationPlanDtlVo> itemList;

}
