package com.sydata.dostrict.reserve.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @date 2022-04-26
 * @description 粮食储备-储备计划-分页参数
 * @version: 1.0
 */
@ApiModel(description = "粮食储备-储备计划-分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ApparitorReservePlanPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "id(计划单号)")
    private String id;

    @ApiModelProperty(value = "计划名称")
    private String jhmc;

    @ApiModelProperty(value = "计划文号")
    private String jhwh;

    @ApiModelProperty(value = "年份如2022")
    private String jhnd;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty(value = "制定计划单位")
    private String jhzddw;

    @ApiModelProperty(value = "开始计划下达时间")
    private LocalDateTime beginJhxdsj;

    @ApiModelProperty(value = "结束计划下达时间")
    private LocalDateTime endJhxdsj;
}
