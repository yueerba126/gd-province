package com.sydata.monitoring.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 流通检测-好粮油企业 编辑DTO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="流通检测-好粮油企业编辑参数", description="流通检测-好粮油企业编辑参数")
public class GoodCompanyAuditDTO {

    @NotBlank(message = "id不能为空")
    @ApiModelProperty(value = "id")
    private String id;

    @NotBlank(message = "指定的审核状态不能为空")
    @ApiModelProperty(value = "状态：PENDING_AUDIT-待审，PASS-审核通过，NOT_PASS-审核不通过")
    private String status;

}
