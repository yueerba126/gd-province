package com.sydata.reserve.manage.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
public class PlanManageAuditPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "轮换计划单号")
    private List<String> lhjhdhs;

    @ApiModelProperty(value = "计划下达单位统一社会信用代码")
    private String jhxddw;

    @ApiModelProperty(value = "计划年度")
    private String jhnd;

    @ApiModelProperty(value = "轮换计划单号")
    private String lhjhdh;

    @ApiModelProperty(value = "计划名称")
    private String jhmc;

    @ApiModelProperty(value = "计划文号")
    private String jhwh;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty(value = "计划下达日期")
    private LocalDate jhxdsj;

    @ApiModelProperty(value = "轮换货位代码")
    private String lhhwdm;

    @ApiModelProperty(value = "轮换类型")
    private String lhlx;
}
