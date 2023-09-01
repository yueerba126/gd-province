package com.sydata.safe.asess.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * @description 考核评审指标考核参数
 * @date 2023/4/13 22:14
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "考核评审指标考核参数")
public class ReviewIndexAssessParam implements Serializable {

    @NotBlank(message = "主键ID不能为空")
    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "考核分数")
    private BigDecimal assessScore;

    @ApiModelProperty(value = "考核减分原因")
    private String assessMinusCause;


    /**********************以下参数不需要传输******************************/

    @JsonIgnore
    @ApiModelProperty(value = "单位考核指标ID", hidden = true)
    private String orgAssessIndexId;
}
