package com.sydata.trade.vo;

import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.trade.domain.InStockSettlement;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author chenzx
 * @Date 2022/8/19 15:24
 * @Description:
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InStockSettlementVo extends InStockSettlement implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindCargo
    @ApiModelProperty(value = "货位名称")
    private String hwmc;

    @DataBindDict(sourceFieldCombination = "jsfs", sourceField = "#jsfs")
    @ApiModelProperty(value = "结算方式名称")
    private String jsfsName;

    @DataBindDict(sourceFieldCombination = "khhh", sourceField = "#yhhbdm")
    @ApiModelProperty(value = "银行行别代码名称")
    private String yhhbdmName;

    @DataBindDict(sourceFieldCombination = "fpzt", sourceField = "#fpzt")
    @ApiModelProperty(value = "发票状态名称")
    private String fpztName;

}
