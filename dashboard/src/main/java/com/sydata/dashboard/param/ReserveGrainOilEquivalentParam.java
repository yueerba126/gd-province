package com.sydata.dashboard.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author lzq
 * @description 报表管理-储备粮油折合报表参数
 * @date 2023/6/21 15:18
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "报表管理-储备粮油折合报表参数")
public class ReserveGrainOilEquivalentParam implements Serializable {

    @NotBlank(message = "行政区域代码不能为空")
    private String id;

    @NotBlank(message = "国代码不能为空")
    @ApiModelProperty(value = "国代码")
    private String countryId;

    @ApiModelProperty(value = "省代码")
    private String provinceId;

    @ApiModelProperty(value = "市代码")
    private String cityId;

    @ApiModelProperty(value = "区代码")
    private String areaId;

    @NotBlank(message = "行政区域类型不能为空")
    @ApiModelProperty(value = "行政区域类型：国、省、市、县")
    private String type;

    @JsonIgnore
    @ApiModelProperty(value = "分组类型", hidden = true)
    private String groupBy;
}
