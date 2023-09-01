package com.sydata.basis.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author lzq
 * @description 基础信息-财务报表明细查询参数
 * @date 2023/3/23 11:36
 */
@ApiModel(description = "基础信息-财务报表明细查询参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FinanceReportDetailParam implements Serializable {

    @NotEmpty(message = "单位代码必传")
    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @NotEmpty(message = "报表时间必传")
    @ApiModelProperty(value = "报表时间")
    private String bbsj;

    @NotEmpty(message = "报表名必传")
    @ApiModelProperty(value = "报表名 01资产负债表 02现金流量表 03利润表")
    private String bbm;
}
