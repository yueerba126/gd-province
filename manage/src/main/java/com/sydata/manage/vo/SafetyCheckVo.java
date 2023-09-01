package com.sydata.manage.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.manage.domain.SafetyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 安全管理
 *
 * @author lzq
 * @date 2022/8/19 11:26
 */
@ApiModel(description = "粮食管理-安全管理VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SafetyCheckVo extends SafetyCheck implements Serializable {

    @DataBindCompany(sourceField = "#dwdm")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindStockHouse(sourceField = "#kqdm")
    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @ApiModelProperty(value = "风险类型名称")
    @DataBindDict(sourceField = "#fxlx", sourceFieldCombination = "fxlx")
    private String fxlxName;

    @ApiModelProperty(value = "风险分级名称")
    @DataBindDict(sourceField = "#fxfj", sourceFieldCombination = "fxfj")
    private String fxfjName;
}