package com.sydata.monitoring.vo;

import com.sydata.monitoring.entity.GrainOilPrice;
import com.sydata.monitoring.entity.GrainOilPriceDtl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhangcy
 * @since 2023/4/24 17:13
 */
@Data
@ApiModel(value = "粮油价格采集明主表VO")
public class GrainOilPriceVo extends GrainOilPrice {

    @ApiModelProperty("明细列表")
    private List<GrainOilPriceDtlVo> details;

}
