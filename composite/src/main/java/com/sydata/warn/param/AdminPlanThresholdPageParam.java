package com.sydata.warn.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author fuql
 * @describe 储备计划阈值设置参数
 * @date 2022-06-30 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "储备计划阈值设置参数")
public class AdminPlanThresholdPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "行政区划代码")
    private String xzqhdm;

    @ApiModelProperty(value = "开始创建时间")
    private LocalDate beginCreateTime;

    @ApiModelProperty(value = "结束创建时间")
    private LocalDate endCreateTime;

}
