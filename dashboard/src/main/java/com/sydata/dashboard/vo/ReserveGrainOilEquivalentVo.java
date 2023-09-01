package com.sydata.dashboard.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sydata.framework.core.jackson.format.BigDecimalFormat;
import com.sydata.framework.core.jackson.serialize.BigDecimalSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lzq
 * @description 储备粮油折合报表VO
 * @date 2023/6/20 9:23
 */
@ApiModel(description = "储备粮油折合报表VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReserveGrainOilEquivalentVo implements Serializable {

    @ApiModelProperty(value = "行政区划ID")
    private String id;

    @ApiModelProperty(value = "组织机构")
    @Excel(name = "组织机构", width = 20, needMerge = true)
    private String name;

    @ApiModelProperty(value = "行政区划类型")
    private String type;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "折合原粮合计(吨)")
    @Excel(name = "折合原粮合计(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal zhyl;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "油及料折油合计(吨)")
    @Excel(name = "油及料折油合计(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal zhyy;
}
