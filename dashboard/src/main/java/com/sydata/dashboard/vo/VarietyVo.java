package com.sydata.dashboard.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * @describe 品种VO
 * @date 2023-02-10 14:10
 */

@ApiModel(description = "品种VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class VarietyVo implements Serializable {

    @JsonIgnore
    @ApiModelProperty(value = "地市")
    private String city;

    @JsonIgnore
    @ApiModelProperty(value = "库点代码")
    private String kddm;

    @ApiModelProperty(value = "粮食品种")
    private String lspzlb;

    @ApiModelProperty(value = "数据（万吨）")
    private BigDecimal sjsl;


}
