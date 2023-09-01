package com.sydata.safe.asess.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lzq
 * @description 抽查计划指标抽查参数
 * @date 2023/2/27 17:51
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "抽查计划指标抽查参数")
public class CheckPlanIndexCheckParam implements Serializable {

    @NotBlank(message = "单位考核指标ID不能为空")
    @ApiModelProperty(value = "单位考核指标ID")
    private String id;

    @ApiModelProperty(value = "抽查分数")
    private BigDecimal checkScore;

    @ApiModelProperty(value = "抽查说明")
    private String checkRemark;

    @ApiModelProperty(value = "抽查文件IDS")
    private String checkFileIds;

    @ApiModelProperty(value = "抽查文件名称S")
    private String checkFileNames;
}
