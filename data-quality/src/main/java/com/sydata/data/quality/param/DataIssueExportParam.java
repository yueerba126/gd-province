package com.sydata.data.quality.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author lzq
 * @description 数据质量-数据问题导出参数
 * @date 2023/6/5 10:31
 */
@ApiModel(description = "数据质量-数据问题导出参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DataIssueExportParam implements Serializable {

    @NotBlank(message = "导出文件名不能为空")
    @ApiModelProperty(value = "导出文件名")
    private String fileName;

    @NotEmpty(message = "导出的库区列表不能为空")
    @ApiModelProperty(value = "库区列表")
    private List<String> stockHouseIds;
}
