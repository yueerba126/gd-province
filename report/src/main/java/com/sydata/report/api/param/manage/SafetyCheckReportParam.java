package com.sydata.report.api.param.manage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
 * @description 安全管理上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "安全管理上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SafetyCheckReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiModelProperty(value = "地点")
    private String dd;

    @ApiModelProperty(value = "风险点编码:6位行政区划代码+8位风险识别日期（YYYYMMDD）+4位风险顺序号")
    private String fxdbm;

    @ApiModelProperty(value = "识别人")
    private String sbr;

    @ApiModelProperty(value = "环节/部位")
    private String hjbw;

    @ApiModelProperty(value = "风险信息")
    private String fxxx;

    @ApiModelProperty(value = "风险类型")
    private String fxlx;

    @ApiModelProperty(value = "风险分级")
    private String fxfj;

    @ApiModelProperty(value = "风险管控措施")
    private String fxglcs;

    @JsonProperty("Yhxx")
    @ApiModelProperty(value = "隐患信息")
    private String yhxx;

    @ApiModelProperty(value = "隐患排查信息")
    private String yhpcxx;

    @ApiModelProperty(value = "隐患整改信息")
    private String yhzgxx;

    @ApiModelProperty(value = "隐患整改验收信息")
    private String yhysxx;

    @ApiModelProperty(value = "事故基本信息")
    private String sgjbxx;

    @ApiModelProperty(value = "整改时限")
    private LocalDate zgsx;

    @ApiModelProperty(value = "责任单位")
    private String zrdw;

    @ApiModelProperty(value = "责任人")
    private String zrr;

    @ApiModelProperty(value = "整改验收信息")
    private String zgysxx;

    @ApiModelProperty(value = "风险跟踪监管责任人")
    private String fxgzjgzrr;
}