package com.sydata.collect.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author lzq
 * @description 数据质量统计年报表分页参数
 * @date 2022/10/11 17:49
 */
@ApiModel(description = "数据质量统计年报表分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class QualityYearsPageParam extends PageQueryParam implements Serializable {

    @NotBlank(message = "年份不能为空")
    @ApiModelProperty(value = "年份")
    private String years;

    @NotBlank(message = "定位必填")
    @Pattern(regexp = "^1$|^2$|^3$|^4$|^5$", message = "定位不存在")
    @ApiModelProperty(value = "定位:1省、2市、3区、4企业、5库区")
    private String partition;

    @ApiModelProperty(value = "是否查看明细")
    private Boolean details;

    @ApiModelProperty(value = "省ID")
    private String provinceId;

    @ApiModelProperty(value = "市ID")
    private String cityId;

    @ApiModelProperty(value = "区ID")
    private String areaId;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;
}
