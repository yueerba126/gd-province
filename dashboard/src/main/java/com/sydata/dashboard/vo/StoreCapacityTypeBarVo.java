package com.sydata.dashboard.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author zhangcy
 * @since 2023/5/5 14:50
 */
@Data
@ApiModel("库容分析-仓房类型仓容利用率柱状图")
public class StoreCapacityTypeBarVo implements Serializable {

    @ApiModelProperty("仓房类型")
    private String cflxdm;

    @DataBindDict(sourceField = "#cflxdm", sourceFieldCombination = "cflx")
    @ApiModelProperty("仓房类型")
    private String cflxmc;

    @ApiModelProperty("设计仓容")
    private BigDecimal sjcr;

    @ApiModelProperty("计价数量")
    private BigDecimal jjsl;

    @ApiModelProperty("全省利用率")
    private BigDecimal useRatioForProvince;

    @ApiModelProperty("库点利用率")
    private BigDecimal useRatioForStoreHouse;

    public void calculateUseRatioForStoreHouse(){
        useRatioForStoreHouse = jjsl.divide(sjcr, 2, RoundingMode.HALF_UP).movePointRight(2);
    }
}
