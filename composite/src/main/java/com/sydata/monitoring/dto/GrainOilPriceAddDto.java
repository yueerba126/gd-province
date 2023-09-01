package com.sydata.monitoring.dto;

import com.sydata.monitoring.entity.GrainOilPrice;
import com.sydata.monitoring.entity.GrainOilPriceDtl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author zhangcy
 * @since 2023/4/24 17:15
 */
@Data
@ApiModel("粮油价格采集明查询参数")
public class GrainOilPriceAddDto extends GrainOilPrice {

    @NotEmpty(message = "明细列表不能为空")
    @ApiModelProperty("明细列表")
    private List<GrainOilPriceDtl> details;
}
