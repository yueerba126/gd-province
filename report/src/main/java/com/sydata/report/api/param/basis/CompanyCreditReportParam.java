package com.sydata.report.api.param.basis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sydata.report.api.param.BaseReportParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author lzq
 * @description 企业信用信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "企业信用信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CompanyCreditReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiModelProperty(value = "信用等级")
    private String xydj;

    @ApiModelProperty(value = "评定年份")
    private String pdnf;

    @ApiModelProperty(value = "评定单位/机构名称")
    private String pddw;
}