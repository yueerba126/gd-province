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
 * @describe 组织操作参数
 * @date 2022-06-30 16:28
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "组织操作参数")
public class OrganizeOperateParam implements Serializable {

    @ApiModelProperty(value = "ID")
    private String id;

    @NotBlank(message = "组织类型不能为空")
    @ApiModelProperty(value = "组织类型：行政组织、企业组织")
    private String kind;

    @ApiModelProperty(value = "组织业务类型：粮食监管单位")
    private String busType;

    @NotBlank(message = "名称不能为空")
    @ApiModelProperty(value = "名称")
    private String name;

    @NotBlank(message = "父组织ID不能为空")
    @ApiModelProperty(value = "父组织ID")
    private String parentId;

    @NotBlank(message = "行政区域ID不能为空")
    @ApiModelProperty(value = "行政区域ID")
    private String regionId;

    @NotBlank(message = "菜单系统ID不能为空")
    @ApiModelProperty(value = "菜单系统ID")
    private String menuSystemId;
}
