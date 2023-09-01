package com.sydata.dashboard.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author xy
 * @describe 仓库实时温度VO
 * @date 2023-02-10 14:10
 */

@ApiModel(description = "仓库实时温度VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TemperatureVo implements Serializable {

    @ApiModelProperty("检测时间")
    private LocalDate jcsj;

    @ApiModelProperty("仓内温度")
    private BigDecimal cfnw;

    @ApiModelProperty("仓外温度")
    private BigDecimal cwwd;

    @ApiModelProperty("仓外湿度")
    private BigDecimal cwsd;

    @ApiModelProperty("最大温度")
    private BigDecimal zdwd;

    @ApiModelProperty("平均温度")
    private BigDecimal pjwd;

    @ApiModelProperty("最小温度")
    private BigDecimal zxwd;



}
