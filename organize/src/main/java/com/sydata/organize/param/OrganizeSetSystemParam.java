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
import java.util.List;

/**
 * @author lzq
 * @describe 组织设置系统参数
 * @date 2022-06-30 19:10
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "组织设置系统参数")
public class OrganizeSetSystemParam implements Serializable {

    @NotBlank(message = "系统ID不能为空")
    @ApiModelProperty(value = "系统ID")
    private String menuSystemId;

    @NotEmpty(message = "组织不能为空")
    @ApiModelProperty(value = "组织IDS")
    private List<String> ids;
}
