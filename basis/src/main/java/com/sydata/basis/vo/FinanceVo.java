package com.sydata.basis.vo;

import com.sydata.basis.domain.Finance;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
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
 * @description 财务报表信息VO
 * @date 2022/12/7 14:43
 */
@ApiModel(description = "财务报表信息VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FinanceVo extends Finance implements Serializable {

    @DataBindStockHouse(sourceField = "#stockHouseId")
    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @DataBindCompany
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindDict(sourceField = "#bbm", sourceFieldCombination = "bbm")
    @ApiModelProperty(value = "报表名称")
    private String bbmName;
}
