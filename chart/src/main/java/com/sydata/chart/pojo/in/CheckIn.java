package com.sydata.chart.pojo.in;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.chart.pojo.CheckResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 入库质检检斤卡片-检验
 * </p>
 *
 * @author lzq
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "入库质检检斤卡片-检验")
public class CheckIn {

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @DataBindDict(sourceFieldCombination = "food_variety", sourceField = "#lspzdm")
    @ApiModelProperty(value = "粮食品种名称")
    private String lspzdmName;

    @DataBindDict(sourceFieldCombination = "food_grade", sourceField = "#lsdjdm")
    @ApiModelProperty(value = "粮食等级名称")
    private String lsdjdmName;

    @ApiModelProperty(value = "检验项目")
    private List<CheckResult> checkResults;

}
