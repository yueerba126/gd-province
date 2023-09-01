package com.sydata.reserve.layout.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.composite.annotation.DataBindReserveCompany;
import com.sydata.common.composite.annotation.DataBindStockHouseRecord;
import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.reserve.layout.domain.WarehouseRecord;
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
 * @describe 储备布局地理信息-仓房信息备案VO
 * @date 2022-07-09 16:29
 */
@ApiModel(description = "储备布局地理信息-仓房信息备案VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WarehouseRecordVo extends WarehouseRecord implements Serializable {


    @DataBindReserveCompany(sourceField = "#dwdm")
    @ApiModelProperty("单位代码名称")
    private String dwdmName;

    @DataBindStockHouseRecord(sourceField = "#kqdm")
    @ApiModelProperty("库区代码名称")
    private String kqdmName;

    @DataBindDict(sourceField = "#cflxdm", sourceFieldCombination = "cflx")
    @ApiModelProperty(value = "仓房类型代码名称")
    private String cflxdmName;

    @DataBindDict(sourceField = "#qtjg", sourceFieldCombination = "qtjg")
    @ApiModelProperty(value = "墙体结构名称")
    private String qtjgName;

    @DataBindDict(sourceField = "#fdjg", sourceFieldCombination = "fdjg")
    @ApiModelProperty(value = "房顶结构名称")
    private String fdjgName;

    @DataBindDict(sourceField = "#fjjg", sourceFieldCombination = "fjjg")
    @ApiModelProperty(value = "房架结构名称")
    private String fjjgName;

    @DataBindDict(sourceField = "#dmjg", sourceFieldCombination = "dmjg")
    @ApiModelProperty(value = "地面结构名称")
    private String dmjgName;

    @DataBindDict(sourceField = "#dlmxs", sourceFieldCombination = "dlmxs")
    @ApiModelProperty(value = "挡粮门型式名称")
    private String dlmxsName;

    @DataBindDict(sourceField = "#cfsfwh", sourceFieldCombination = "cfsfwh")
    @ApiModelProperty(value = "仓房是否完好名称")
    private String cfsfwhName;

    @DataBindDict(sourceField = "#clgx", sourceFieldCombination = "clgx")
    @ApiModelProperty(value = "储粮功效名称")
    private String clgxName;

    @DataBindDict(sourceField = "#nfgrbw", sourceFieldCombination = "nfgrbw")
    @ApiModelProperty(value = "能否隔热保温名称")
    private String nfgrbwName;

    @DataBindDict(sourceField = "#grxn", sourceFieldCombination = "grxn")
    @ApiModelProperty(value = "隔热性能名称")
    private String grxnName;

    @DataBindDict(sourceField = "#jgcl", sourceFieldCombination = "jgcl")
    @ApiModelProperty(value = "结构材料名称")
    private String jgclName;

    @DataBindDict(sourceField = "#qmx", sourceFieldCombination = "qmx")
    @ApiModelProperty(value = "气密性名称")
    private String qmxName;

    @DataBindDict(sourceField = "#sfyjxxxhgz", sourceFieldCombination = "sfyjxxxhgz")
    @ApiModelProperty(value = "是否已进行信息化改造名称")
    private String sfyjxxxhgzName;

    @DataBindDict(sourceField = "#lqjs", sourceFieldCombination = "lqjs")
    @ApiModelProperty(value = "粮情技术名称")
    private String lqjsName;

    @DataBindDict(sourceField = "#nfszcc", sourceFieldCombination = "nfszcc")
    @ApiModelProperty(value = "能否散装储存名称")
    private String nfszccName;

    @DataBindDict(sourceField = "#ywfsfqfczz", sourceFieldCombination = "ywfsfqfczz")
    @ApiModelProperty(value = "有无防鼠防雀防虫装置及设施名称")
    private String ywfsfqfczzName;

    @DataBindDict(sourceField = "#ywfhfbfdss", sourceFieldCombination = "ywfhfbfdss")
    @ApiModelProperty(value = "有无防火防爆防盗设施名称")
    private String ywfhfbfdssName;

    @DataBindDict(sourceField = "#ywjxtfss", sourceFieldCombination = "ywjxtfss")
    @ApiModelProperty(value = "有无机械通风设施名称")
    private String ywjxtfssName;

    @DataBindDict(sourceField = "#tfxtxs", sourceFieldCombination = "tfxtxs")
    @ApiModelProperty(value = "通风系统型式名称")
    private String tfxtxsName;

    @DataBindDict(sourceField = "#tfjs", sourceFieldCombination = "tfjs")
    @ApiModelProperty(value = "通风技术名称")
    private String tfjsName;

    @DataBindDict(sourceField = "#nfhlxzsc", sourceFieldCombination = "nfhlxzsc")
    @ApiModelProperty(value = "能否环流熏蒸杀虫名称")
    private String nfhlxzscName;

    @DataBindDict(sourceField = "#scjs", sourceFieldCombination = "scjs")
    @ApiModelProperty(value = "杀虫技术名称")
    private String scjsName;

    @DataBindDict(sourceField = "#nffddyqtcl", sourceFieldCombination = "nffddyqtcl")
    @ApiModelProperty(value = "能否富氮低氧气调储粮名称")
    private String nffddyqtclName;

    @DataBindDict(sourceField = "#kwjs", sourceFieldCombination = "kwjs")
    @ApiModelProperty(value = "控温技术名称")
    private String kwjsName;

    @DataBindDict(sourceField = "#hcjcfs", sourceFieldCombination = "hcjcfs")
    @ApiModelProperty(value = "害虫检测方式名称")
    private String hcjcfsName;

    @DataBindDict(sourceField = "#cfzt", sourceFieldCombination = "cfzt")
    @ApiModelProperty(value = "仓房状态名称")
    private String cfztName;
}
