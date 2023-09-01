package com.sydata.dashboard.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindStockHouse;
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
 * @describe 粮情告警排名VO
 * @date 2023-02-10 14:10
 */

@ApiModel(description = "粮情告警排名VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WarningVo implements Serializable {

    @ApiModelProperty(value = "单位名称")
    @DataBindCompany(sourceField = "#dwdm")
    private String dwmc;

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiModelProperty(value = "当日告警数")
    private BigDecimal dayNum;

    @ApiModelProperty(value = "当月告警数")
    private BigDecimal monthNum;

}
