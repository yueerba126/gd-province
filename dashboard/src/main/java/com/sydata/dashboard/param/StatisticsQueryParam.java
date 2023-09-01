package com.sydata.dashboard.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 报表管理参数
 *
 * @author fuql
 * @date 2023-02-10 14:10
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "报表管理参数")
public class StatisticsQueryParam implements Serializable {

    @ApiModelProperty(value = "省ID", hidden = true)
    private String provinceId;

    @ApiModelProperty(value = "市ID", hidden = true)
    private String cityId;

    @ApiModelProperty(value = "区ID", hidden = true)
    private String areaId;
}
