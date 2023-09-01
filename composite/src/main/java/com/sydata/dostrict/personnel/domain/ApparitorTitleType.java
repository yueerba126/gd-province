package com.sydata.dostrict.personnel.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.composite.annotation.DataBindTitleType;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 行政管理-称号类别管理对象 apparitor_title_type
 *
 * @author fuql
 * @date 2023-04-25
 */
@ApiModel(description = "行政管理-称号类别管理")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("apparitor_title_type")
public class ApparitorTitleType extends BaseFiledEntity implements Serializable {


    @Excel(name = "称号类别管理Id")
    @ApiModelProperty(value = "称号类别管理Id")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @Excel(name = "分类名称")
    @ApiModelProperty(value = "分类名称")
    private String typeName;

    @Excel(name = "父类ID")
    @ApiModelProperty(value = "父类ID")
    private String parentId;

    @TableField(exist = false)
    @ApiModelProperty(value = "上级分类名称")
    @DataBindTitleType(sourceField = "#parentId")
    private String parentName;

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

    @Excel(name = "删除标志(0正常 1被删除)")
    @ApiModelProperty(value = "删除标志(0正常 1被删除)")
    private String delFlag;
}
