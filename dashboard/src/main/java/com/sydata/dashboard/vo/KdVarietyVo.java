package com.sydata.dashboard.vo;

import com.sydata.common.basis.annotation.DataBindStockHouse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author xy
 * @describe 库点品种统计VO
 * @date 2023-02-10 14:10
 */

@ApiModel(description = "库点品种统计VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class KdVarietyVo implements Serializable {

    @ApiModelProperty(value = "库点名称")
    @DataBindStockHouse(sourceField = "#kddm")
    private String kdmc;


    @ApiModelProperty(value = "库点")
    private String kddm;

    @ApiModelProperty(value = "总量（含商品粮）（万吨）")
    private Double gross;

    @ApiModelProperty(value = "品种")
    private List<VarietyVo> lspzlb;


}
