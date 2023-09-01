package com.sydata.manage.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author ：lixi
 * @date ：Created in 2022/5/11 10:24
 * @description：质检信息列表查询参数
 * @version: 1.0
 */
@Data
public class QualityInspectionPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键id 货位代码+质检报告单号")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "开始签发日期")
    private LocalDate beginQfrq;

    @ApiModelProperty(value = "结束签发日期")
    private LocalDate endQfrq;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;

}
