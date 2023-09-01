package com.sydata.dashboard.vo;

import com.sydata.common.basis.annotation.DataBindStockHouse;
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
 * @describe 库点上报排名VO
 * @date 2023-02-10 14:10
 */

@ApiModel(description = "库点上报排名VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RepositoryReportVo implements Serializable {

    @ApiModelProperty(value = "库点名称")
    @DataBindStockHouse(sourceField = "#kddm")
    private String kqmc;

    @ApiModelProperty(value = "库点代码")
    private String kddm;

    @ApiModelProperty(value = "本月出库量（吨）")
    private BigDecimal byckl;

    @ApiModelProperty(value = "库存量（万吨）")
    private BigDecimal sjsl;

}
