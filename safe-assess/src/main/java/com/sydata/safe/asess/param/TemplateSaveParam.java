package com.sydata.safe.asess.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lzq
 * @description 考核模板保存参数
 * @date 2023/3/13 14:14
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "考核模板保存参数")
public class TemplateSaveParam implements Serializable {

    @NotBlank(message = "模板编号必填")
    @ApiModelProperty(value = "模板编号")
    private String number;

    @NotBlank(message = "模板名称必填")
    @ApiModelProperty(value = "模板名称")
    private String name;

    @NotBlank(message = "年份必填")
    @ApiModelProperty(value = "年份")
    private String year;

    @NotNull(message = "考核分占比必填")
    @ApiModelProperty(value = "考核分占比")
    private BigDecimal assessScoreProportion;

    @NotNull(message = "抽查分占比必填")
    @ApiModelProperty(value = "抽查分占比")
    private BigDecimal checkScoreProportion;
}
