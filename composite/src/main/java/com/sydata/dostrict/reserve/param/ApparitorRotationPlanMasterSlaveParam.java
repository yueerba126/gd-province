package com.sydata.dostrict.reserve.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author lzq
 * @date 2022-04-26
 * @description 粮食储备-轮换计划信息-主从表操作参数
 * @version: 1.0
 */
@ApiModel(description = "粮食储备-轮换计划信息-主从表操作参数")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorRotationPlanMasterSlaveParam extends ApparitorRotationPlanSaveParam implements Serializable {

    @ApiModelProperty(value = "轮换计划明细信息列表参数")
    private List<ApparitorRotationPlanDtlSaveParam> itemList;

}
