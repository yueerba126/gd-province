package com.sydata.dostrict.personnel.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.composite.annotation.DataBindFileType;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_NULL;

/**
 * 行政管理-文件类别管理对象 apparitor_file_type
 *
 * @author fuql
 * @date 2023-04-24
 */
@ApiModel(description = "行政管理-文件类别管理")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("apparitor_file_type")
public class ApparitorFileType extends BaseFiledEntity implements Serializable {

    @Excel(name = "文件类别管理Id")
    @ApiModelProperty(value = "文件类别管理Id")
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
    @DataBindFileType(sourceField = "#parentId")
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

    @Excel(name = "租户ID")
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

}