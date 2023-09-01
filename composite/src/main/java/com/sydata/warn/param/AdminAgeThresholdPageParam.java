package com.sydata.warn.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author fuql
 * @describe 库存年限告警阈值设置分页参数
 * @date 2022-06-30 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "库存年限告警阈值设置分页参数")
public class AdminAgeThresholdPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "粮食品种代码 字典：food_big_variety")
    private String ylpz;

    @ApiModelProperty(value = "开始创建时间")
    private LocalDate beginCreateTime;

    @ApiModelProperty(value = "结束创建时间")
    private LocalDate endCreateTime;

}
