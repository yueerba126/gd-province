package com.sydata.monitoring.dto;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 流通检测-好粮油企业查询DTO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="流通检测-好粮油企业查询参数", description="流通检测-好粮油企业查询参数")
public class GoodCompanyQueryDTO extends PageQueryParam {

    @ApiModelProperty(value = "企业名称")
    private String companyName;
}
