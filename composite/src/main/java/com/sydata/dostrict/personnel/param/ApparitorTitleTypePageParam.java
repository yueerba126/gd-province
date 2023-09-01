package com.sydata.dostrict.personnel.param;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author fuql
 * @describe 行政管理-称号类别分页参数
 * @date 2022-06-30 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "行政管理-称号类别分页参数")
public class ApparitorTitleTypePageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "文件类别管理Id")
    private Long typeId;

    @Excel(name = "分类名称")
    @ApiModelProperty(value = "分类名称")
    private String typeName;

    @Excel(name = "父类ID")
    @ApiModelProperty(value = "父类ID")
    private Long parentId;

    @Excel(name = "所有上级id")
    @ApiModelProperty(value = "所有上级id")
    private String allParentId;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;

    @Excel(name = "是否叶节点(1是 0否)")
    @ApiModelProperty(value = "是否叶节点(1是 0否)")
    private String isLeafNode;

    @Excel(name = "启用状态(1正常 0停用)")
    @ApiModelProperty(value = "启用状态(1正常 0停用)")
    private String isEnable;
}
