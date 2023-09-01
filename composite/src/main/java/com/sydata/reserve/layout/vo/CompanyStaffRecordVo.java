package com.sydata.reserve.layout.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.reserve.layout.domain.CompanyStaffRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @describe 储备布局地理信息-人员信息备案VO
 * @date 2022-08-19
 */
@ApiModel(description = "储备布局地理信息-人员信息备案VO")
@Data
@ToString
@Accessors(chain = true)
public class CompanyStaffRecordVo extends CompanyStaffRecord implements Serializable {


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
}
