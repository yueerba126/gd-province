package com.sydata.monitoring.dto;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangcy
 * @since 2023/4/24 11:36
 */
@ApiModel(value = "监测点查询参数")
@Data
@EqualsAndHashCode(callSuper = false)
public class CheckPointQueryDto extends PageQueryParam {

    @ApiModelProperty(value = "监测点名称")
    private String name;

}
