package com.sydata.admin.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ：lixi
 * @date ：Created in 2022/8/19 17:54
 * @description：轮换计划明细查询条件
 * @version: 1.0
 */
@Data
public class RotationPlanDtlPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键ID(计划明细单号)")
    private String id;

    @ApiModelProperty(value = "轮换计划单号")
    private String lhjhdh;

    @ApiModelProperty(value = "计划明细单号")
    private String jhmxdh;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
