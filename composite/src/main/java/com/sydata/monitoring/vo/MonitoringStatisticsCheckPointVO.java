package com.sydata.monitoring.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.monitoring.entity.MonitoringStatisticsCheckPoint;

import com.sydata.organize.annotation.DataBindUser;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
/**
 * <p>
 * 流通检测-粮食市场监测点信息 VO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MonitoringStatisticsCheckPoint对象", description="流通检测-粮食市场监测点信息")
public class MonitoringStatisticsCheckPointVO extends MonitoringStatisticsCheckPoint implements Serializable {

    @ApiModelProperty(value = "主键ID")
    private String pointId;

    @ApiModelProperty(value = "经度")
    private String jd;

    @ApiModelProperty(value = "纬度")
    private String wd;

    @ApiModelProperty(value = "监测点名称")
    private String name;

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

    @ApiModelProperty(value = "监测点账号ID")
    private String accountUserId;

    public MonitoringStatisticsCheckPointVO(MonitoringStatisticsCheckPoint monitoringStatisticsCheckPoint) {
        if (monitoringStatisticsCheckPoint != null) {
            BeanUtils.copyProperties(monitoringStatisticsCheckPoint, this);
        }
    }
    public void fillProperty(CheckPointVo checkPointVo) {
        if (checkPointVo != null) {
            setPointId(checkPointVo.getId());
            setJd(checkPointVo.getJd());
            setWd(checkPointVo.getWd());
            setName(checkPointVo.getName());
            setAccount(checkPointVo.getAccount());
            setStatusName(checkPointVo.getStatusName());
            setCreateByName(checkPointVo.getCreateByName());
            setUpdateByName(checkPointVo.getUpdateByName());
            setEnterpriseName(checkPointVo.getEnterpriseName());
            setAccountUserId(checkPointVo.getAccountUserId());
        }
    }

}
