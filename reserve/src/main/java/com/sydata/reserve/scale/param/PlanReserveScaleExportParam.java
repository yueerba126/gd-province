package com.sydata.reserve.scale.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author fuql
 * @describe 储备规模管理分页参数
 * @date 2022-06-30 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "储备规模管理设置分页参数")
public class PlanReserveScaleExportParam implements Serializable {

    @ApiModelProperty(value = "ids")
    private List<String> ids;

    @ApiModelProperty(value = "行政区划代码")
    private String regionCode;

    @ApiModelProperty(value = "上级行政区划代码")
    private String regionParentCode;

    @ApiModelProperty(value = "开始创建时间")
    private LocalDate beginCreateTime;

    @ApiModelProperty(value = "结束创建时间")
    private LocalDate endCreateTime;

}
