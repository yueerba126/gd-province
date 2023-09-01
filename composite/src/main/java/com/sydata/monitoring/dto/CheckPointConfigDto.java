package com.sydata.monitoring.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhangcy
 * @since 2023/4/24 11:36
 */
@ApiModel(value = "监测点配置参数")
@Data
@EqualsAndHashCode(callSuper = false)
public class CheckPointConfigDto {

    @NotNull(message = "监测点ID不能为空")
    @ApiModelProperty("监测点ID")
    private String id;

    @ApiModelProperty("品种列表：字典food_variety")
    private List<String> materialTypeList;

    @ApiModelProperty("价格列表：字典monitoring_price")
    private List<String> priceList;

}
