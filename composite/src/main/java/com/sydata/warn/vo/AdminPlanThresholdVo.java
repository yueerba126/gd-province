package com.sydata.warn.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.organize.annotation.DataBindRegion;
import com.sydata.warn.domain.AdminAgeThreshold;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 储备计划阈值设置VO
 *
 * @author fuql
 * @date 2022-08-19
 */
@ApiModel(description = "储备计划阈值设置VO")
@Data
@ToString
@Accessors(chain = true)
public class AdminPlanThresholdVo extends AdminAgeThreshold implements Serializable {

    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty("行政区划名称")
    private String xzqhdmName;

    @DataBindDict(sourceField = "#ylpz", sourceFieldCombination = "food_big_variety")
    @ApiModelProperty(value = "粮食品种名称")
    private String ylpzName;

}
