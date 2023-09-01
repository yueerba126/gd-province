package com.sydata.manage.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindTank;
import com.sydata.common.basis.annotation.DataBindWarehouse;
import com.sydata.manage.domain.SteamTaskInformation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 蒸熏作业信息
 * </p>
 *
 * @author lzq
 * @since 2022-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SteamTaskInformation对象", description = "蒸熏作业信息")
public class SteamTaskInformationVo extends SteamTaskInformation implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindWarehouse
    @DataBindTank(sourceField = "#cfdm")
    @ApiModelProperty(value = "仓房名称")
    private String cfdmName;

    @ApiModelProperty(value = "常规熏蒸方式名称")
    @DataBindDict(sourceField = "#cgxzfs", sourceFieldCombination = "cgxzfs")
    private String cgxzfsName;

    @ApiModelProperty(value = "环流熏蒸方式名称")
    @DataBindDict(sourceField = "#hlxzfs", sourceFieldCombination = "hlxzfs")
    private String hlxzfsName;

    @ApiModelProperty(value = "环流熏蒸与内环流技术结合名称")
    @DataBindDict(sourceField = "#hlxzynhljsjh", sourceFieldCombination = "hlxzynhljsjh")
    private String hlxzynhljsjhName;

    @ApiModelProperty(value = "施药方法名称")
    @DataBindDict(sourceField = "#syff", sourceFieldCombination = "syff")
    private String syffName;

    @ApiModelProperty(value = "熏蒸效果评价名称")
    @DataBindDict(sourceField = "#xzxgpj", sourceFieldCombination = "xzxgpj")
    private String xzxgpjName;

}
