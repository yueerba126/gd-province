package com.sydata.basis.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * @author lzq
 * @describe 基础信息-字典删除参数
 * @date 2022-07-26 10:25
 */
@ApiModel(description = "基础信息-字典删除参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DictRemoveParam implements Serializable {

    @NotBlank(message = "字典类型不能为空")
    @NotNull(message = "字典类型不能为空")
    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @NotNull(message = "字典键不能为空")
    @NotEmpty(message = "字典键不能为空")
    @ApiModelProperty(value = "字典键集合")
    private Set<String> dictKeys;

    @NotNull(message = "字典值不能为空")
    @NotEmpty(message = "字典值不能为空")
    @ApiModelProperty(value = "字典值集合")
    private Set<String> dictValues;
}
