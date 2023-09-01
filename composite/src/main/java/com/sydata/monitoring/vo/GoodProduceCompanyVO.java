package com.sydata.monitoring.vo;

import com.sydata.monitoring.enums.MonitoringAuditStatusEnum;
import com.sydata.monitoring.entity.GoodProduceCompany;
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
 * 流通检测-放心粮油企业 VO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "MonitoringGoodProduceCompany对象", description = "流通检测-放心粮油企业")
public class GoodProduceCompanyVO extends GoodProduceCompany implements Serializable {

    @ApiModelProperty(value = "是否配送中心")
    private String ifDistributionCenterName;

    @ApiModelProperty(value = "是否经营店")
    private String ifSaleShopName;

    @ApiModelProperty(value = "是否进学校")
    private String ifSaleSchoolName;

    @ApiModelProperty(value = "状态：PENDING_AUDIT-待审，PASS-审核通过，NOT_PASS-审核不通过")
    private String statusName;

    public GoodProduceCompanyVO() {
    }

    public GoodProduceCompanyVO(GoodProduceCompany monitoringGoodProduceCompany) {
        if (monitoringGoodProduceCompany != null) {
            BeanUtils.copyProperties(monitoringGoodProduceCompany, this);

            setIfSaleSchoolName(getIfSaleSchool() != null && getIfSaleSchool() == 1 ? "是" : "否");
            setIfDistributionCenterName(getIfDistributionCenter() != null && getIfDistributionCenter() == 1 ? "是" : "否");
            setIfSaleShopName(getIfSaleShop() != null && getIfSaleShop() == 1 ? "是" : "否");
            setStatusName(Optional.of(MonitoringAuditStatusEnum.valueOf(getStatus())).map(MonitoringAuditStatusEnum::getName).orElse(null));
        }

    }


}
