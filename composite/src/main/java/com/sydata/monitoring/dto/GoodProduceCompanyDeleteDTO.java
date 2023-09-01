package com.sydata.monitoring.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * 流通检测-放心粮油企业 删除DTO
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="流通检测-放心粮油企业删除参数", description="流通检测-放心粮油企业删除参数")
public class GoodProduceCompanyDeleteDTO {

    @ApiModelProperty(value = "id列表")
    @NotEmpty(message = "id不能为空")
    private List<String> ids;

}
