package com.sydata.admin.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ：lixi
 * @date ：Created in 2022/8/19 17:54
 * @description：轮换计划查询条件
 * @version: 1.0
 */
@Data
public class RotationPlanPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键ID(轮换计划单号)")
    private String id;

    @ApiModelProperty(value = "计划下达单位")
    private String jhxddw;

    @ApiModelProperty(value = "轮换计划单号")
    private String lhjhdh;

    @ApiModelProperty(value = "计划文号")
    private String jhwh;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
