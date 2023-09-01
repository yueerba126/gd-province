package com.sydata.manage.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindTank;
import com.sydata.common.basis.annotation.DataBindWarehouse;
import com.sydata.manage.domain.Ventilation;
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
 * @describe 通风作业VO
 * @date 2022-07-25 11:37
 */
@ApiModel(description = "粮库管理-通风作业VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class VentilationVo extends Ventilation implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindWarehouse
    @DataBindTank(sourceField = "#cfdm")
    @ApiModelProperty(value = "仓库名称")
    private String cfmc;

    @ApiModelProperty(value = "通风目的名称")
    @DataBindDict(sourceField = "#tfmd", sourceFieldCombination = "tfmd")
    private String tfmdName;

    @ApiModelProperty(value = "通风类型名称")
    @DataBindDict(sourceField = "#tflx", sourceFieldCombination = "tflx")
    private String tflxName;

    @ApiModelProperty(value = "风道型式名称")
    @DataBindDict(sourceField = "#fdxs", sourceFieldCombination = "fdxs")
    private String fdxsName;

    @ApiModelProperty(value = "风网设置方式名称")
    @DataBindDict(sourceField = "#fwszfs", sourceFieldCombination = "fwszfs")
    private String fwszfsName;

    @ApiModelProperty(value = "送风方式名称")
    @DataBindDict(sourceField = "#sffs", sourceFieldCombination = "sffs")
    private String sffsName;
}
