package com.sydata.report.api.param.manage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sydata.report.api.param.BaseReportParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @description 气体信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "气体信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GasInformationReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "气体浓度检测单号:由货位代码+检测日期（yyyyMMdd）+4位顺序号组成")
    private String qtndjcdh;

    @ApiModelProperty(value = "检测时间")
    private LocalDateTime jcsj;

    @ApiModelProperty(value = "货位编码")
    private String hwdm;

    @ApiModelProperty(value = "氧气含量值集合")
    private String yqhlzjh;

    @ApiModelProperty(value = "二氧化碳含量集合")
    private String eyhthlzje;

    @ApiModelProperty(value = "硫酰氟浓度集合")
    private String lxfndzjh;

    @ApiModelProperty(value = "磷化氢浓度集合")
    private String lhqndzjh;

    @ApiModelProperty(value = "一氧化氮含量集合")
    private String yyhdhlzjh;

    @ApiModelProperty(value = "一氧化碳含量集合")
    private String yyhthlzjh;

    @ApiModelProperty(value = "作业类型")
    private String zylx;
}
