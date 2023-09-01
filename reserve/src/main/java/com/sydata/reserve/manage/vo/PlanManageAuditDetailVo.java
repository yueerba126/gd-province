package com.sydata.reserve.manage.vo;

import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.reserve.manage.domain.PlanManageAuditDtl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 轮换计划审核详情表返回值
 *
 * @author fuql
 * @date 2023-05-19 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "轮换计划审核详情表返回值")
public class PlanManageAuditDetailVo extends PlanManageAuditDtl implements Serializable {

    @DataBindCargo(sourceField = "#lhhwdm")
    @ApiModelProperty(value = "轮换货位名称", hidden = true)
    private String lhhwdmName;

    @ApiModelProperty(value = "粮食品种名称")
    @DataBindDict(sourceField = "#lspzdm", sourceFieldCombination = "food_variety")
    private String lspzdmName;
}
