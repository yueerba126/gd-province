package com.sydata.warn.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 库存数量-粮食库存异常告警分页参数
 *
 * @author fuql
 * @date 2022-06-30 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "库存数量-粮食库存异常告警分页参数")
public class WarnFoodstuffMessagePageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "预警时间开始")
    private LocalDateTime startWarnDate;

    @ApiModelProperty(value = "预警时间结束")
    private LocalDateTime endWarnDate;

    @ApiModelProperty(value = "处理时间开始")
    private LocalDateTime startHandleDate;

    @ApiModelProperty(value = "处理时间结束")
    private LocalDateTime endHandleDate;

    @ApiModelProperty(value = "预警等级字典：warn_level")
    private String warnLevel;

    @ApiModelProperty(value = "预警类型字典：warn_type")
    private String warnType;

}
