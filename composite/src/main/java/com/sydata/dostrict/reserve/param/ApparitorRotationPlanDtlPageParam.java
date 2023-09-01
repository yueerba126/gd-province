package com.sydata.dostrict.reserve.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @date 2022-04-26
 * @description：粮食储备-轮换计划明细信息-分页参数
 * @version: 1.0
 */
@ApiModel(description = "粮食储备-轮换计划明细信息-分页参数")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorRotationPlanDtlPageParam extends PageQueryParam implements Serializable {

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
