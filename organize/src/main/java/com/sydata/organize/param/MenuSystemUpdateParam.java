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
@ApiModel(description = "菜单系统修改参数")
public class MenuSystemUpdateParam extends MenuSystemSaveParam implements Serializable {

    @NotBlank(message = "主键不能为空")
    @ApiModelProperty(value = "主键ID")
    private String id;
}
