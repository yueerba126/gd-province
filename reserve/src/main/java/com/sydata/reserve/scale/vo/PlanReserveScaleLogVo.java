package com.sydata.reserve.scale.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.sydata.organize.annotation.DataBindRegion;
import com.sydata.reserve.scale.domain.PlanReserveScale;
import com.sydata.reserve.scale.domain.PlanReserveScaleLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 储备计划管理-储备规模调整日志Vo
 *
 * @author fuql
 * @date 2022-08-19
 */
@ApiModel(description = "储备计划管理-储备规模调整日志Vo")
@Data
@ToString
@Accessors(chain = true)
public class PlanReserveScaleLogVo extends PlanReserveScaleLog implements Serializable {

    @Excel(name = "行政区域名称", width = 20, needMerge = true)
    @DataBindRegion(sourceField = "#regionCode")
    @ApiModelProperty(value = "行政区域名称")
    private String regionName;

    @DataBindRegion(sourceField = "#regionParentCode")
    @ApiModelProperty(value = "行政区划父级名称")
    private String regionParentName;
}
