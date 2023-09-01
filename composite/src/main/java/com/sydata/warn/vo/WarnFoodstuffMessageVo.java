package com.sydata.warn.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.warn.domain.WarnFoodstuffMessage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * 库存数量-粮食库存异常告警VO
 *
 * @author fuql
 * @date 2022-08-19
 */
@ApiModel(description = "库存数量-粮食库存异常告警VO")
@Data
@ToString
@Accessors(chain = true)
public class WarnFoodstuffMessageVo extends WarnFoodstuffMessage implements Serializable {

    @ApiModelProperty(value = "预警等级字典：warn_level")
    @DataBindDict(sourceField = "#warnLevel", sourceFieldCombination = "warn_level")
    private String warnLevelName;

    @ApiModelProperty(value = "预警类型字典：warn_type")
    @DataBindDict(sourceField = "#warnType", sourceFieldCombination = "warn_type")
    private String warnTypeName;

    @ApiModelProperty(value = "单位名称")
    @DataBindCompany
    private String dwmc;

    @ApiModelProperty(value = "处理状态字典：handle_status")
    @DataBindDict(sourceField = "#handleStatus", sourceFieldCombination = "handle_status")
    private String handleStatusName;
}
