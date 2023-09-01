package com.sydata.chart.pojo;

import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 检验项目实体类
 * @author lzq
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "检验项目类")
public class CheckResult {

    @ApiModelProperty(value = "检验项目")
    private String jyxm;

    @DataBindDict(sourceField = "#jyxm", sourceFieldCombination = "jyxm")
    @ApiModelProperty(value = "检验项目名称")
    private String jyxmName;

    @ApiModelProperty(value = "检验项目值")
    private String jyxmz;
}
