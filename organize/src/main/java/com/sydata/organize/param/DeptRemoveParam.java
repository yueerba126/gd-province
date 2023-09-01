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
 * @describe 部门删除参数
 * @date 2022-06-30 17:07
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "部门删除参数")
public class DeptRemoveParam implements Serializable {

    @NotNull(message = "ID不能为空")
    @ApiModelProperty(value = "ID")
    private Long id;

    @NotBlank(message = "父部门IDS不能为空")
    @ApiModelProperty(value = "父部门IDS")
    private String parentIds;

    @NotBlank(message = "名称不能为空")
    @ApiModelProperty(value = "名称")
    private String name;

    @NotBlank(message = "组织ID不能为空")
    @ApiModelProperty(value = "组织ID")
    private String organizeId;

}
