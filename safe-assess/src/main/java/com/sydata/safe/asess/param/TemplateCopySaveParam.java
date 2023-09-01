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
import java.math.BigDecimal;
import java.util.List;

/**
 * @author lzq
 * @description 考核模板复制保存参数
 * @date 2023/3/13 14:14
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "考核模板复制保存参数")
public class TemplateCopySaveParam extends TemplateSaveParam implements Serializable {

    @NotBlank(message = "模板ID不能为空")
    @ApiModelProperty(value = "模板ID")
    private String id;
}
