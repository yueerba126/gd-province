package com.sydata.monitoring.dto;

import com.sydata.monitoring.entity.GrainOilPriceDtl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author zhangcy
 * @since 2023/4/24 17:15
 */
@Data
@ApiModel("粮油价格采集明细查询参数")
public class GrainOilPriceDtlQueryDto extends GrainOilPriceDtl {
    @ApiModelProperty("开始单据日期")
    private LocalDate beginBillDate;

    @ApiModelProperty("结束单据日期")
    private LocalDate endBillDate;

    @ApiModelProperty("主表ID列表")
    private Set<String> mainIds;

    @ApiModelProperty(value = "当前页", example = "1")
    public int pageNum = 1;

    @ApiModelProperty(value = "每页数量", example = "10")
    private int pageSize = 10;

}
