package com.sydata.reserve.scale.param;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author fuql
 * @describe 储备规模管理设置分页参数
 * @date 2022-06-30 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "储备规模管理设置分页参数")
public class PlanReserveScalePageParam extends PageQueryParam implements Serializable {

    @Excel(name = "上级行政区划代码", width = 20, needMerge = true)
    @ApiModelProperty(value = "上级行政区划代码")
    private String regionParentCode;

    @ApiModelProperty(value = "行政区域ID")
    private String regionId;

    @ApiModelProperty(value = "ids")
    private List<String> ids;

}
