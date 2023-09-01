package com.sydata.safe.asess.param;

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
 * @description 单位考核导出参数
 * @date 2023/2/23 17:16
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "单位考核导出参数")
public class OrgAssessExportParam implements Serializable {

    @NotBlank(message = "考核模板ID不能为空")
    @ApiModelProperty(value = "考核模板ID")
    private String templateId;

    @NotEmpty(message = "单位考核ID列表不能为空")
    @ApiModelProperty(value = "单位考核ID列表")
    private List<String> ids;
}
