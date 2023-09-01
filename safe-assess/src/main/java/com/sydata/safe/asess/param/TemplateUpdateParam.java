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
 * @description 考核模板编辑参数
 * @date 2023/3/13 14:14
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "考核模板编辑参数")
public class TemplateUpdateParam extends TemplateSaveParam implements Serializable {

    @NotBlank(message = "模板ID必填")
    @ApiModelProperty(value = "模板ID")
    private String id;

    @NotBlank(message = "状态必填")
    @ApiModelProperty(value = "状态：保存、待分配")
    private String state;

    @ApiModelProperty(value = "自动分配部门IDS")
    private String autoAllotDeptIds;

    @NotBlank(message = "行政区域不能为空")
    @ApiModelProperty(value = "行政区域IDS")
    private String regionIds;
}
