package com.sydata.dostrict.plan.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 规划建设-仓储设施投资管理-查询参数
 *
 * @author lzq
 * @date 2023/04/24 18:30
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "规划建设-仓储设施投资管理-查询参数")
public class WarehousInvestManageSelectParam implements Serializable {

    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "单位代码格式错误")
    @ApiModelProperty("单位代码")
    private String dwdm;

    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "库区代码格式错误")
    @ApiModelProperty("库区代码")
    private String kqdm;

    @Pattern(regexp = "^\\d{6}$", message = "行政区划代码格式错误")
    @ApiModelProperty("行政区划代码")
    private String xzqhdm;

}
