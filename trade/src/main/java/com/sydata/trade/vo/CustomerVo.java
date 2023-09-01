package com.sydata.trade.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.trade.domain.Customer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @describe 粮油购销-客户VO
 * @date 2022-07-22 19:24
 */
@ApiModel(description = "粮油购销-客户VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CustomerVo extends Customer implements Serializable {

    @DataBindCompany(sourceField = "#dwdm")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindStockHouse(sourceField = "#kqdm")
    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @ApiModelProperty("客户类型名称")
    @DataBindDict(sourceField = "#khlx", sourceFieldCombination = "khlx")
    private String khlxName;

    @ApiModelProperty("客户方开户行名称")
    @DataBindDict(sourceField = "#khfkhh", sourceFieldCombination = "bank_type")
    private String khfkhhName;
}
