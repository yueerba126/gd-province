package com.sydata.dashboard.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
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
 * @describe 库区基本信息VO
 * @date 2023-02-10 14:10
 */

@ApiModel(description = "库区基本信息VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GranaryBasisVo implements Serializable {

    @ApiModelProperty(value = "单位名称")
    @DataBindCompany(sourceField = "#dwdm")
    private String dwmc;

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @ApiModelProperty(value = "仓房数量")
    private BigDecimal cfs;

    @ApiModelProperty(value = "油罐数量")
    private BigDecimal ygs;

    @ApiModelProperty(value = "廒间数量")
    private BigDecimal ajs;

    @ApiModelProperty(value = "货位数量")
    private BigDecimal hws;

}
