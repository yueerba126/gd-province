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
import java.util.List;

/**
 * @author xy
 * @describe 地市品种统计VO
 * @date 2023-02-10 14:10
 */

@ApiModel(description = "地市品种统计VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CityVarietyVo implements Serializable {

    @ApiModelProperty(value = "地市名称")
    @DataBindRegion(sourceField = "#city")
    private String cityName;

    @ApiModelProperty(value = "地市")
    private String city;

    @ApiModelProperty(value = "总量（含商品粮）（万吨）")
    private Double gross;

    @ApiModelProperty(value = "品种")
    private List<VarietyVo> lspzlb;


}
