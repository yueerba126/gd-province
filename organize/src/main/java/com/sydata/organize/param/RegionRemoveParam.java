
package com.sydata.organize.param;

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

/**
 * @author lzq
 * @describe 行政区域删除参数
 * @date 2022-06-29 17:49
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "行政区域删除参数")
public class RegionRemoveParam implements Serializable {

    @NotBlank(message = "行政区域代码不能为空")
    @ApiModelProperty(value = "行政区域代码")
    private String id;

    @NotNull(message = "父行政区域代码不能为空")
    @ApiModelProperty(value = "父行政区域代码")
    private String parentId;

    @NotBlank(message = "行政区域名称不能为空")
    @ApiModelProperty(value = "行政区域名称")
    private String name;
}
