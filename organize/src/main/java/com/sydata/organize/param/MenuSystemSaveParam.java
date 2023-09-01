
package com.sydata.organize.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author lzq
 * @description 菜单系统新增参数
 * @date 2023/5/22 15:06
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "菜单系统新增参数")
public class MenuSystemSaveParam implements Serializable {

    @NotBlank(message = "系统名称不能为空")
    @Size(max = 100, message = "系统名称最大长度为100个字符")
    @ApiModelProperty(value = "系统名称")
    private String name;

    @NotBlank(message = "系统简称不能为空")
    @Size(max = 30, message = "系统简称最大长度为30个字符")
    @ApiModelProperty(value = "系统简称")
    private String shortName;

    @NotBlank(message = "系统图标不能为空")
    @Size(max = 60, message = "系统图标最大长度为60个字符")
    @ApiModelProperty(value = "系统图标")
    private String logo;

    @Size(max = 200, message = "备注最大长度为200个字符")
    @ApiModelProperty(value = "备注")
    private String remark;
}
