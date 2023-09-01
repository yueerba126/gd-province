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

/**
 * @author lzq
 * @description 考核指标删除参数
 * @date 2023/2/13 15:12
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "考核指标删除参数")
public class IndexRemoveParam implements Serializable {

    @NotBlank(message = "主键ID必填")
    @ApiModelProperty(value = "主键ID")
    private String id;

    @NotBlank(message = "考核模板ID必填")
    @ApiModelProperty(value = "考核模板ID")
    private String templateId;
}
