package com.sydata.report.api.param.manage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sydata.report.api.param.BaseReportParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * @author lzq
 * @description 温湿度监测信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "温湿度监测信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GrainMonitorReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "温湿度检测单号:货位代码+检测日期（yyyyMMdd）+4位顺序号组成")
    private String wsdjcdh;

    @ApiModelProperty(value = "检测时间")
    private LocalDateTime jcsj;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "仓房外温")
    private BigDecimal cfww;

    @ApiModelProperty(value = "仓房外湿")
    private BigDecimal cfws;

    @ApiModelProperty(value = "仓房内温")
    private BigDecimal cfnw;

    @ApiModelProperty(value = "仓房内湿")
    private BigDecimal cfns;

    @ApiModelProperty(value = "粮食最高温")
    private BigDecimal lszgw;

    @ApiModelProperty(value = "粮食最低温")
    private BigDecimal lszdw;

    @ApiModelProperty(value = "粮食平均温")
    private BigDecimal lspjw;

    @ApiModelProperty(value = "粮食温度值集合")
    private String lswdzjh;

    @ApiModelProperty(value = "粮食湿度值集合")
    private String lssdzjh;
}