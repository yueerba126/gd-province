package com.sydata.record.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindTank;
import com.sydata.common.basis.annotation.DataBindWarehouse;
import com.sydata.record.domain.FumigationDtl;
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
 * @description 备案管理-熏蒸明细VO
 * @date 2022/12/10 13:25
 */
@ApiModel(description = "备案管理-熏蒸明细VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FumigationDtlVo extends FumigationDtl implements Serializable {

    @DataBindWarehouse(sourceField = "#cfdm")
    @DataBindTank(sourceField = "#cfdm")
    @ApiModelProperty(value = "仓房(或油罐)名称")
    private String cfdmName;

    @DataBindDict(sourceFieldCombination = "food_variety", sourceField = "#lspzdm")
    @ApiModelProperty(value = "粮食品种名称")
    private String lspzdmName;

    @DataBindDict(sourceFieldCombination = "food_nature", sourceField = "#lsxzdm")
    @ApiModelProperty(value = "粮食性质名称")
    private String lsxzdmName;

    @DataBindDict(sourceFieldCombination = "food_grade", sourceField = "#lsdjdm")
    @ApiModelProperty(value = "粮食等级名称")
    private String lsdjdmName;

    @DataBindDict(sourceFieldCombination = "clfs", sourceField = "#clfs")
    @ApiModelProperty(value = "储粮方式名称")
    private String clfsName;

    @DataBindDict(sourceField = "#cldjpd", sourceFieldCombination = "cldjpd")
    @ApiModelProperty(value = "虫粮等级判定名称")
    private String cldjpdName;

    @DataBindDict(sourceField = "#qmx", sourceFieldCombination = "qmx")
    @ApiModelProperty(value = "气密性名称")
    private String qmxName;
}
