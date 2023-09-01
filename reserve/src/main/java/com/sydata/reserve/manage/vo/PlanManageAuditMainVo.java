package com.sydata.reserve.manage.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.reserve.manage.domain.PlanManageAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 轮换计划审核主表返回值
 *
 * @author fuql
 * @date 2023-05-19 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "轮换计划审核主表返回值")
public class PlanManageAuditMainVo extends PlanManageAudit implements Serializable {


    @DataBindDict(sourceFieldCombination = "food_nature", sourceField = "#lsxzdm")
    @ApiModelProperty(value = "粮食性质代码名称")
    private String lsxzdmName;

}
