package com.sydata.dostrict.personnel.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.composite.annotation.DataBindFileType;
import com.sydata.dostrict.personnel.domain.ApparitorSystem;
import com.sydata.organize.annotation.DataBindDept;
import com.sydata.organize.annotation.DataBindUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author fuql
 * @describe 行政管理-人员制度管理VO
 * @date 2022-08-19
 */
@ApiModel(description = "行政管理-人员制度管理VO")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorSystemMainVo extends ApparitorSystem implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "组织名称")
    private String enterpriseName;

    @DataBindDept
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "发布人")
    @DataBindUser(sourceField = "#releaseId")
    private String releaseName;

    @ApiModelProperty(value = "文件类别id")
    @DataBindFileType(sourceField = "#fileTypeId")
    private String fileTypeName;

    @ApiModelProperty(value = "单据状态")
    @DataBindDict(sourceField = "#billStatus", sourceFieldCombination = "apparitor_status")
    private String billStatusName;
}
