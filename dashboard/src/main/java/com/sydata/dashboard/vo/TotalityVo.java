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
 * @describe 库点总数VO
 * @date 2023-02-10 14:10
 */

@ApiModel(description = "库点总数VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TotalityVo implements Serializable {



    @ApiModelProperty(value = "库点总数")
    private BigDecimal kdzs;

    @ApiModelProperty(value = "上报库点数")
    private BigDecimal sbkds;

}
