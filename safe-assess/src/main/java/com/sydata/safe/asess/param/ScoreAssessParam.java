
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
 * @description 考核评分考核参数
 * @date 2023/4/3 16:51
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "考核评分考核参数")
public class ScoreAssessParam implements Serializable {

    @NotBlank(message = "主键ID不能为空")
    @ApiModelProperty(value = "主键ID")
    private String id;

    @NotBlank(message = "状态不能为空")
    @ApiModelProperty(value = "状态：待评分、已评分")
    private String state;

    @NotEmpty(message = "考核评分指标考核参数列表不能为空")
    @ApiModelProperty(value = "考核评分指标考核参数列表")
    private List<ScoreIndexAssessParam> list;
}
