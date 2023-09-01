package com.sydata.organize.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @describe 行政区域分页参数
 * @date 2022-06-29 17:49
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "行政区域分页参数")
public class RegionPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "行政区域代码")
    private String id;

    @ApiModelProperty(value = "父级行政区域代码")
    private String parentId;

    @ApiModelProperty(value = "省代码")
    private String provinceId;

    @ApiModelProperty(value = "市代码")
    private String cityId;

    @ApiModelProperty(value = "区代码")
    private String areaId;

    @ApiModelProperty(value = "行政区域类型：国、省、市、县")
    private String type;

    @ApiModelProperty(value = "行政区域名称")
    private String name;
}
