package com.sydata.chart.pojo.quality;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.chart.pojo.CheckResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 质检报告卡片-质检结果
 * </p>
 *
 * @author lzq
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "质检报告卡片-质检结果")
public class QualityCheckResult extends CheckResult {

    @DataBindDict(sourceField = "#jyxm", sourceFieldCombination = "jyxm", dataValue = "#dictFarType")
    @ApiModelProperty(value = "指标类别")
    private String zblb;

    @DataBindDict(sourceField = "#zblb", sourceFieldCombination = "zblb")
    @ApiModelProperty(value = "指标类别名称")
    private String zblbName;

}

