package com.sydata.manage.vo;

import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.manage.domain.QualityInspection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COMMA;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.HASH;
import static com.sydata.framework.databind.handle.value.bind.ValueBindStrategy.SEPARATED;

/**
 * <p>
 * 质检信息表
 * </p>
 *
 * @author lzq
 * @since 2022-05-08
 */
@Data
public class QualityInspectionVo extends QualityInspection implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindCargo
    @ApiModelProperty("货位名称")
    private String hwmc;

    @DataBindDict(sourceFieldCombination = "food_variety", sourceField = "#lspzdm")
    @ApiModelProperty(value = "粮食品种名称")
    private String lspzdmName;

    @DataBindDict(sourceFieldCombination = "food_grade", sourceField = "#lsdjdm")
    @ApiModelProperty(value = "粮食等级名称")
    private String lsdjdmName;

    @DataBindDict(sourceFieldCombination = "food_grade", sourceField = "#ypdj")
    @ApiModelProperty(value = "样品等级名称")
    private String ypdjName;

    @ApiModelProperty(value = "检验类别名称")
    @DataBindDict(sourceField = "#jylb", sourceFieldCombination = "jylb")
    private String jylbName;

    @ApiModelProperty(value = "检验依据名称")
    @DataBindDict(sourceField = "#jyyj", sourceFieldCombination = "jyyj")
    private String jyyjName;

    @DataBindDict(sourceField = "#zblb", sourceFieldCombination = "zblb", valueBindStrategy = SEPARATED, bindSeparated = HASH)
    @ApiModelProperty(value = "指标类别名称")
    private String zblbName;

    @DataBindDict(sourceField = "#jyxm", sourceFieldCombination = "jyxm", valueBindStrategy = SEPARATED, bindSeparated = COMMA)
    @ApiModelProperty(value = "检验项目名称")
    private String jyxmName;

    @ApiModelProperty(value = "是否正常存储年限名称")
    @DataBindDict(sourceField = "#sfzcccnx", sourceFieldCombination = "sfzcccnx")
    private String sfzcccnxName;
}
