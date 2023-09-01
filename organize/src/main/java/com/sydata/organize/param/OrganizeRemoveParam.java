
package com.sydata.organize.param;

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
 * @describe 组织删除参数
 * @date 2022-06-29 17:49
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "组织删除参数")
public class OrganizeRemoveParam implements Serializable {

    @NotBlank(message = "组织ID不能为空")
    @ApiModelProperty(value = "组织ID")
    private String id;

    @NotBlank(message = "组织名称不能为空")
    @ApiModelProperty(value = "组织名称")
    private String name;

    @NotBlank(message = "父组织ID集合不能为空")
    @ApiModelProperty(value = "父组织ID集合,分隔")
    private String parentIds;
}
