package com.sydata.dashboard.vo;

import com.sydata.organize.annotation.DataBindRegion;
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
 * @author xy
 * @describe 库存实物比例VO
 * @date 2023-02-10 14:10
 */

@ApiModel(description = "库存实物比例VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PhysicalProportionVo implements Serializable {

    @ApiModelProperty(value = "地市名称")
    @DataBindRegion(sourceField = "#city")
    private String cityName;

    @ApiModelProperty(value = "地市编码")
    private String city;

    @ApiModelProperty(value = "储备规模量(万吨)")
    private BigDecimal cbgml;

    @ApiModelProperty(value = "库存实物比例")
    private BigDecimal kcswbl;

}
