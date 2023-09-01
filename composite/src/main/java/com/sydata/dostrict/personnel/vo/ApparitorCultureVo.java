package com.sydata.dostrict.personnel.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.dostrict.personnel.domain.ApparitorCulture;
import com.sydata.dostrict.personnel.enums.CultureBillStatusEnums;
import com.sydata.organize.annotation.DataBindDept;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author fuql
 * @describe 行政管理-人才培养计划信息VO
 * @date 2022-08-19
 */
@ApiModel(description = "行政管理-人才培养计划信息VO")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorCultureVo extends ApparitorCulture implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "组织名称")
    private String enterpriseName;

    @DataBindDept
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "单据状态")
    private String billStatusName;

    public String getBillStatusName() {
        if (CultureBillStatusEnums.SAVE.getCode().equals(super.getBillStatus())) {
            return CultureBillStatusEnums.SAVE.getName();
        } else if (CultureBillStatusEnums.AUDIT.getCode().equals(super.getBillStatus())){
            return CultureBillStatusEnums.AUDIT.getName();
        } else if (CultureBillStatusEnums.RELEASE.getCode().equals(super.getBillStatus())){
            return CultureBillStatusEnums.RELEASE.getName();
        }
        return "";
    }
}
