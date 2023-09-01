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
 * @describe 储备规模调整日志分页参数
 * @date 2022-06-30 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "储备规模调整日志分页参数")
public class PlanReserveScaleLogPageParam extends PageQueryParam implements Serializable {

    @Excel(name = "上级行政区划代码", width = 20, needMerge = true)
    @ApiModelProperty(value = "上级行政区划代码")
    private String regionParentCode;
}
