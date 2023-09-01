package com.sydata.monitoring.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.monitoring.entity.CheckPoint;
import com.sydata.organize.annotation.DataBindUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangcy
 * @since 2023/4/24 11:35
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CheckPointVo extends CheckPoint {

    @ApiModelProperty(value = "主键ID")
    private String pointId;

    @DataBindUser(sourceField = "#accountUserId", dataValue = "#account")
    @ApiModelProperty(value = "监测点账号")
    private String account;

    @ApiModelProperty(value = "状态：1-正常，0-禁用")
    private String statusName;

    @DataBindUser(sourceField = "#createBy", queryColumn = "account")
    @ApiModelProperty(value = "添加人员")
    private String createByName;

    @DataBindUser(sourceField = "#updateBy", queryColumn = "account")
    @ApiModelProperty(value = "更新者")
    private String updateByName;

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;

    public CheckPointVo(CheckPoint source) {
        if (source != null) {
            setPointId(source.getId());
            setId(source.getId());
            setName(source.getName());
            setStatus(source.getStatus());
            setJd(source.getJd());
            setWd(source.getWd());
            setAccountUserId(source.getAccountUserId());
            setEnterpriseId(source.getEnterpriseId());
            setCreateBy(source.getCreateBy());
            setCreateTime(source.getCreateTime());
            setUpdateBy(source.getUpdateBy());
            setUpdateTime(source.getUpdateTime());
            setStatusName(getStatus() != null && getStatus() ? "正常" : "禁用");
        }
    }
}
