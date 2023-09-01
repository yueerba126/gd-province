package com.sydata.organize.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lzq
 * @describe 菜单删除参数
 * @date 2022-06-30 19:10
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "菜单删除参数")
public class MenuRemoveParam implements Serializable {

    @NotNull(message = "ID不能为空")
    @ApiModelProperty(value = "ID")
    private Long id;

    @NotNull(message = "父ID不能为空")
    @ApiModelProperty(value = "父ID")
    private Long parentId;
}
