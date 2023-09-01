package com.sydata.report.api.param.basis;

import com.fasterxml.jackson.annotation.JsonInclude;
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
 * @description 货位信息上报参数
 * @date 2022/10/31 16:19
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "货位信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CargoReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty("货位代码")
    private String hwdm;

    @ApiModelProperty("货位名称")
    private String hwmc;

    @ApiModelProperty("廒间代码")
    private String ajdm;

    @ApiModelProperty("货位启用日期")
    private LocalDate hwqyrq;

    @ApiModelProperty("货位容量")
    private BigDecimal hwrl;

    @ApiModelProperty("保管单位")
    private String bgdw;

    @ApiModelProperty("保管员")
    private String bgy;
}
