package com.sydata.chart.pojo.quality;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 质检报告卡片-质检人员
 * </p>
 *
 * @author lzq
 * @since 2022-10-26
 */
@Data
@ApiModel(description = "质检报告卡片-质检人员")
public class People {

    @ApiModelProperty(value = "扦样人姓名")
    private String qyrxm;

    @ApiModelProperty(value = "检验人")
    private String jyr;

    @ApiModelProperty(value = "扦样时间")
    private LocalDateTime qysj;

    @ApiModelProperty(value = "检验时间")
    private LocalDateTime jysj;

    @ApiModelProperty(value = "报告出具时间")
    private LocalDateTime bgcjsj;
}
