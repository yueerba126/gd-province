package com.sydata.organize.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @describe 组织分页参数
 * @date 2022-06-30 16:28
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "组织分页参数")
public class OrganizePageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "组织类型：行政组织、企业组织")
    private String kind;

    @ApiModelProperty(value = "组织业务类型：粮食监管单位")
    private String busType;

    @ApiModelProperty(value = "菜单系统ID")
    private String menuSystemId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "行政区域ID")
    private String regionId;
}
