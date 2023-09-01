package com.sydata.report.api.param.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sydata.common.domain.BaseFiledEntity;
import com.sydata.report.api.param.BaseReportParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * @author lzq
 * @description 倒仓信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "倒仓信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransferBarnReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "倒仓单号")
    private String dcdh;

    @ApiModelProperty(value = "倒仓类型:0:倒仓 1:移库")
    private String dclx;

    @ApiModelProperty(value = "倒仓计划文件编号")
    private String dcjhwjbh;

    @ApiModelProperty(value = "通知单号")
    private String tzdh;

    @ApiModelProperty(value = "倒出库区 关联表 E.2 库区代码")
    private String dcdw;

    @ApiModelProperty(value = "倒入库区 关联表 E.2 库区代码")
    private String drdw;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "倒出货位编码")
    private String dchwdm;

    @ApiModelProperty(value = "导入货位编码")
    private String drhwdm;

    @ApiModelProperty(value = "倒仓日期")
    private LocalDate dcrq;

    @ApiModelProperty(value = "倒仓数量")
    private BigDecimal dcsl;

    @ApiModelProperty(value = "包装物")
    private String bzw;

    @ApiModelProperty(value = "标准包件数")
    private Integer bzbjs;

    @ApiModelProperty(value = "装卸作业单位")
    private String zxzydw;
}