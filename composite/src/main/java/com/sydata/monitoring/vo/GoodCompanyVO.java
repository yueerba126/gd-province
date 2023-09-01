package com.sydata.monitoring.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindFile;
import com.sydata.monitoring.enums.MonitoringAuditStatusEnum;
import com.sydata.monitoring.entity.GoodCompany;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Optional;

/**
 * <p>
 * 流通检测-好粮油企业 VO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MonitoringGoodCompany对象", description="流通检测-好粮油企业")
public class GoodCompanyVO extends GoodCompany implements Serializable {

    @ApiModelProperty(value = "状态：PENDING_AUDIT-待审，PASS-审核通过，NOT_PASS-审核不通过")
    private String statusName;

    @DataBindDict(sourceField = "#companyType", sourceFieldCombination = "dwlx")
    @ApiModelProperty(value = "企业类型")
    private String companyTypeName;

//    @DataBindDict(sourceField = "#companyNature", sourceFieldCombination = "")
    @ApiModelProperty(value = "企业性质")
    private String companyNatureName;

    @DataBindDict(sourceField = "#stockStoreType", sourceFieldCombination = "food_nature")
    @ApiModelProperty(value = "仓储业务类型")
    private String stockStoreTypeName;

    @DataBindFile(sourceField = "#businessLicenseFile")
    @ApiModelProperty(value = "营业执照文件名")
    private String businessLicenseFileName;

    public GoodCompanyVO() {
    }

    public GoodCompanyVO(GoodCompany monitoringGoodCompany) {
        if (monitoringGoodCompany != null) {
            BeanUtils.copyProperties(monitoringGoodCompany, this);
            setStatusName(Optional.of(MonitoringAuditStatusEnum.valueOf(getStatus())).map(MonitoringAuditStatusEnum::getName).orElse(null));
        }

    }
}
