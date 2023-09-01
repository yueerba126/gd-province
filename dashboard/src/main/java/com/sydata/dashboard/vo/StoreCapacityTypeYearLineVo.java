package com.sydata.dashboard.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zhangcy
 * @since 2023/5/5 14:50
 */
@Data
@ApiModel("库容分析-仓房类型年代折线图")
public class StoreCapacityTypeYearLineVo implements Serializable {

    @ApiModelProperty("仓房类型")
    private String cflxdm;

    @DataBindDict(sourceField = "#cflxdm", sourceFieldCombination = "cflx")
    @ApiModelProperty("仓房类型")
    private String cflxmc;

    @ApiModelProperty("80年代前的容量")
    private BigDecimal capacityBefore80Year;

    @ApiModelProperty("80到98年的容量")
    private BigDecimal capacityBefore98Year;

    @ApiModelProperty("八九年至今的容量")
    private BigDecimal capacityBeforeNowYear;
}
