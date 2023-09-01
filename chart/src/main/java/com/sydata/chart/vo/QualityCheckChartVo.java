package com.sydata.chart.vo;

import com.sydata.chart.pojo.quality.People;
import com.sydata.chart.pojo.quality.QualityCheckResult;
import com.sydata.chart.pojo.quality.Sample;
import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 质检报告卡片
 * </p>
 *
 * @author lzq
 * @since 2022-05-08
 */
@ApiModel(description = "质检报告卡片Vo")
@Data
@ToString
@Accessors(chain = true)
public class QualityCheckChartVo implements Serializable {

    @DataBindDict(sourceFieldCombination = "food_variety", sourceField = "#lspz")
    @ApiModelProperty(value = "质检报告(品种)")
    private String zjbgpz;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspz;

    @ApiModelProperty(value = "编码")
    private String bm;

    @ApiModelProperty(value = "样品信息")
    private Sample ypxx;

    @ApiModelProperty(value = "检验单位")
    private String jydw;

    @ApiModelProperty(value = "质检人员")
    private People zjry;

    @ApiModelProperty(value = "质检结果")
    private List<QualityCheckResult> zjjg;

    @ApiModelProperty(value = "备注")
    private String bz;

}
