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
import java.time.LocalDate;

/**
 * @author lzq
 * @description 考核模板发布参数
 * @date 2023/2/13 15:12
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "考核模板发布参数")
public class TemplatePushParam implements Serializable {

    @NotBlank(message = "考核模板ID必填")
    @ApiModelProperty(value = "考核模板ID")
    private String id;

    @NotNull(message = "最晚提交日期必填")
    @ApiModelProperty(value = "最晚提交日期")
    private LocalDate lastSubmitDate;
}
