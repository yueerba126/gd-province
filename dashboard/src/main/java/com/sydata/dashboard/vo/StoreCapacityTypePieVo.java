package com.sydata.dashboard.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.dashboard.domain.DashboardWarehouseCapacity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author zhangcy
 * @since 2023/5/5 14:50
 */
@Data
@Accessors(chain = true)
@ApiModel("库容分析-仓房类型占比饼图")
public class StoreCapacityTypePieVo implements Serializable {

    @DataBindDict(sourceField = "#cflxdm", sourceFieldCombination = "cflx")
    @ApiModelProperty("仓房类型")
    private String cflxmc;

    @ApiModelProperty("仓房类型代码")
    private String cflxdm;

    @ApiModelProperty("设计仓容")
    private BigDecimal sjcr;

    @ApiModelProperty("占比")
    private BigDecimal proportion;

    public StoreCapacityTypePieVo(DashboardWarehouseCapacity source) {
        if (source != null) {
            setCflxdm(source.getCflxdm());
            setSjcr(source.getSjcr());
        }
    }

    public StoreCapacityTypePieVo calculateProportion(BigDecimal totalCapacity) {
        setProportion(sjcr.divide(totalCapacity, 2, RoundingMode.HALF_UP).movePointRight(2));
        return this;
    }
}
