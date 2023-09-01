package com.sydata.dostrict.personnel.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.organize.annotation.DataBindRegion;
import com.sydata.dostrict.personnel.domain.ApparitorPersonnel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author fuql
 * @describe 基础信息-企业人员信息VO
 * @date 2022-08-19
 */
@ApiModel(description = "基础信息-企业人员信息VO")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorPersonnelVo extends ApparitorPersonnel implements Serializable {

    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty("行政区划名称")
    private String xzqhdmName;

    @DataBindDict(sourceField = "#xb", sourceFieldCombination = "ryxb")
    @ApiModelProperty("性别名称")
    private String xbName;

    @DataBindDict(sourceField = "#gwxz", sourceFieldCombination = "gwxz")
    @ApiModelProperty("岗位性质名称")
    private String gwxzName;

    @DataBindDict(sourceField = "#zgzt", sourceFieldCombination = "zgzt")
    @ApiModelProperty("在岗状态名称")
    private String zgztName;

    @DataBindDict(sourceField = "#mz", sourceFieldCombination = "mz")
    @ApiModelProperty("民族名称")
    private String mzName;

    @DataBindDict(sourceField = "#zzmm", sourceFieldCombination = "zzmm")
    @ApiModelProperty("政治面貌名称")
    private String zzmmName;

    @DataBindDict(sourceField = "#rylb", sourceFieldCombination = "rylb")
    @ApiModelProperty("人员类别名称")
    private String rylbName;

    @DataBindDict(sourceField = "#xl", sourceFieldCombination = "xl")
    @ApiModelProperty("学历名称")
    private String xlName;

    @DataBindStockHouse
    @ApiModelProperty(value = "库区名称")
    private String kqmc;
}
