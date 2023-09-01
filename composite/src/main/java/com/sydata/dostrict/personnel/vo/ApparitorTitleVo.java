package com.sydata.dostrict.personnel.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.composite.annotation.DataBindTitleType;
import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.dostrict.personnel.domain.ApparitorTitle;
import com.sydata.organize.annotation.DataBindDept;
import com.sydata.common.composite.annotation.DataBindPersonnel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author fuql
 * @describe 行政管理-荣誉称号信息VO
 * @date 2022-08-19
 */
@ApiModel(description = "行政管理-荣誉称号信息VO")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorTitleVo extends ApparitorTitle implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "组织名称")
    private String enterpriseName;

    @DataBindDept
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @DataBindPersonnel(sourceField = "#personnelId")
    @ApiModelProperty(value = "人员名称")
    private String personnelName;

    @DataBindTitleType(sourceField = "#titleTypeId")
    @ApiModelProperty(value = "称号类别")
    private String titleTypeName;
}
