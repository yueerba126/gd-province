package com.sydata.dostrict.casetarget.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.dostrict.casetarget.domain.ApparitorCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author fuql
 * @describe 行政管理-执法案件VO
 * @date 2022-08-19
 */
@ApiModel(description = "行政管理-执法案件VO")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorCaseVo extends ApparitorCase implements Serializable {

    @ApiModelProperty(value = "案件企业")
    @DataBindOrganize(sourceField = "#caseEnterpriseId")
    private String caseEnterpriseName;

    @ApiModelProperty(value = "案件类型")
    @DataBindDict(sourceField = "#caseType", sourceFieldCombination = "case_type")
    private String caseTypeName;

    @ApiModelProperty(value = "案件来源 字典：case_source")
    @DataBindDict(sourceField = "#caseSource", sourceFieldCombination = "case_source")
    private String caseSourceName;
}
