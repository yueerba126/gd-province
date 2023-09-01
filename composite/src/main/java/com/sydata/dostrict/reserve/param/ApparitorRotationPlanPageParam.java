package com.sydata.dostrict.reserve.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @date 2022-04-26
 * @description：粮食储备-轮换计划-分页参数
 * @version: 1.0
 */
@ApiModel(description = "粮食储备-轮换计划-分页参数")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorRotationPlanPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键ID(轮换计划单号)")
    private String id;

    @ApiModelProperty(value = "计划名称")
    private String jhmc;

    @ApiModelProperty(value = "计划年度")
    private String jhnd;

    @ApiModelProperty(value = "计划下达单位")
    private String jhxddw;

    @ApiModelProperty(value = "轮换计划单号")
    private String lhjhdh;

    @ApiModelProperty(value = "计划文号")
    private String jhwh;

    @ApiModelProperty(value = "开始计划下达时间")
    private LocalDateTime beginJhxdsj;

    @ApiModelProperty(value = "结束计划下达时间")
    private LocalDateTime endJhxdsj;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
