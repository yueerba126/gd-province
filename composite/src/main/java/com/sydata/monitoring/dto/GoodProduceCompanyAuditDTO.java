package com.sydata.monitoring.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 流通检测-放心粮油企业 审核DTO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="流通检测-放心粮油企业审核参数", description="流通检测-放心粮油企业审核参数")
public class GoodProduceCompanyAuditDTO{

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "状态：PENDING_AUDIT-待审，PASS-审核通过，NOT_PASS-审核不通过")
    private String status;
}
