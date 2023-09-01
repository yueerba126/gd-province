package com.sydata.basis.vo;

import com.sydata.basis.domain.Granary;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindTank;
import com.sydata.common.basis.annotation.DataBindWarehouse;
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
 * @describe 基础信息-廒间信息VO
 * @date 2022-07-09 16:38
 */
@ApiModel(description = "基础信息-廒间信息VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GranaryVo extends Granary implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindWarehouse(sourceField = "#cfbh")
    @DataBindTank(sourceField = "#cfbh")
    @ApiModelProperty(value = "仓房(或油罐)名称")
    private String cfmc;

    @DataBindDict(sourceField = "#ajzt", sourceFieldCombination = "ajzt")
    @ApiModelProperty(value = "廒间状态名称")
    private String ajztName;

}
