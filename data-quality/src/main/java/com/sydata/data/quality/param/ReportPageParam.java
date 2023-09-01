package com.sydata.data.quality.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author lzq
 * @description 数据质量-报告分页参数
 * @date 2023/4/25 11:28
 */
@ApiModel(description = "数据质量-报告分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReportPageParam extends PageQueryParam implements Serializable {

    @NotEmpty(message = "报告类型必填")
    @ApiModelProperty(value = "报告类型列表(1库区 2单位 3地区 4地市 5省份)")
    private List<Integer> targetTypes;

    @ApiModelProperty(value = "报告类型具体的ID列表")
    private List<String> targetIds;

    @ApiModelProperty(value = "报告日期开始")
    private LocalDate beginReportDate;

    @ApiModelProperty(value = "报告日期结束")
    private LocalDate endReportDate;

    @ApiModelProperty(value = "国ID")
    private String countryId;

    @ApiModelProperty(value = "省ID")
    private String provinceId;

    @ApiModelProperty(value = "市ID")
    private String cityId;

    @ApiModelProperty(value = "区ID")
    private String areaId;
}
