package com.sydata.reserve.plan.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 储备计划管理-储备计划新增参数
 *
 * @author fuql
 * @date 2023-05-19 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "储备计划管理-储备计划新增参数")
public class PlanReservePlanPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "计划文号")
    private String planCode;

    @ApiModelProperty(value = "计划名称")
    private String planName;

    @ApiModelProperty(value = "粮食性质")
    private String ylxz;

    @ApiModelProperty(value = "承储企业")
    private String ccqy;

    @ApiModelProperty(value = "状态")
    private String billStatus;

    @ApiModelProperty(value = "计划下达时间")
    private LocalDate jhxdsj;

    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @ApiModelProperty(value = "年份如2022")
    private String jhnd;

    @ApiModelProperty(value = "制定计划单位")
    private String jhzddw;

    @ApiModelProperty(value = "计划单号")
    private String jhdh;

}
