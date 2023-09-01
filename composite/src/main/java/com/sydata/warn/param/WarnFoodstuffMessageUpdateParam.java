package com.sydata.warn.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 库存数量-粮食库存异常告警修改参数
 *
 * @author fuql
 * @date 2022-06-30 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "库存数量-粮食库存异常告警修改参数")
public class WarnFoodstuffMessageUpdateParam implements Serializable {

    @ApiModelProperty(value = "库存数量-粮食库存异常告警id")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "处理状态字典：handle_status")
    private String handleStatus;

    @ApiModelProperty(value = "处理时间")
    private LocalDateTime handleDate;

    @ApiModelProperty(value = "处理人 暂时手填")
    private String handlePeople;

    @ApiModelProperty(value = "备注")
    private String remark;

}
