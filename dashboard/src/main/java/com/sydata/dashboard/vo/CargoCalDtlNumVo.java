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
import java.time.LocalDateTime;

/**
 * @author xy
 * @describe 货位卡详情下列VO
 * @date 2023-02-10 14:10
 */

@ApiModel(description = "货位卡详情下列VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CargoCalDtlNumVo implements Serializable {


    @ApiModelProperty(value = "结存数")
    private BigDecimal jcs;

    @ApiModelProperty(value = "入库数")
    private BigDecimal rks;

    @ApiModelProperty(value = "出库数")
    private BigDecimal cks;

    @ApiModelProperty(value = "时间")
    private LocalDate zhgxsj;




}
