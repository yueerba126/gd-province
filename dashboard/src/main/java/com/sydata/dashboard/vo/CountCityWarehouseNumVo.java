package com.sydata.dashboard.vo;

import com.sydata.organize.annotation.DataBindRegion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zhangcy
 * @since 2023/5/7 9:19
 */
@Data
@ApiModel("统计各地市仓房数量")
public class CountCityWarehouseNumVo implements Serializable {

    @ApiModelProperty("地市ID")
    private String cityId;

    @DataBindRegion(sourceField = "#cityId")
    @ApiModelProperty("地市名称")
    private String cityName;

    @ApiModelProperty("仓房个数")
    private BigDecimal num;

}
