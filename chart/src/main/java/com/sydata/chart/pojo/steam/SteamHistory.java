package com.sydata.chart.pojo.steam;

import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;


/**
 * 熏蒸作业-历年熏蒸情况
 *
 * @author lzq
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "熏蒸作业-历年熏蒸情况")
public class SteamHistory {

    @ApiModelProperty("第几次蒸熏")
    private Integer djcxz;

    @ApiModelProperty("熏蒸方法")
    private String syff;

    @ApiModelProperty("熏蒸方法名称")
    @DataBindDict(sourceField = "#syff", sourceFieldCombination = "syff")
    private String syffName;

    @ApiModelProperty(value = "总用药量")
    private BigDecimal zyyl;

}
