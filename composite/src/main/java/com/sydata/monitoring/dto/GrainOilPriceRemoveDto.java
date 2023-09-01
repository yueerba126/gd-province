package com.sydata.monitoring.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author zhangcy
 * @since 2023/4/24 11:36
 */
@ApiModel(value = "删除参数")
@Data
@EqualsAndHashCode(callSuper = false)
public class GrainOilPriceRemoveDto {

    @NotEmpty(message = "ID列表不能为空")
    @ApiModelProperty("ID列表")
    private List<String> ids;
}
