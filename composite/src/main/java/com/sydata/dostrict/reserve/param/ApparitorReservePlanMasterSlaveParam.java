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
 * @description 粮食储备-储备计划-主从表操作参数
 * @version: 1.0
 */
@ApiModel(description = "粮食储备-储备计划-主从表操作参数")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorReservePlanMasterSlaveParam extends ApparitorReservePlanSaveParam implements Serializable {

    @ApiModelProperty(value = "储备规模列表参数")
    private List<ApparitorReserveScaleSaveParam> itemList;

}
