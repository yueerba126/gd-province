package com.sydata.basis.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

/**
 * 基础信息-字典对象 basis_dict
 *
 * @author lzq
 * @date 2022-07-26
 */
@ApiModel(description = "基础信息-字典")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("basis_dict")
public class Dict implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "字典顺序")
    private Integer dictSort;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "字典键")
    private String dictKey;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "字典值")
    private String dictValue;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "字典顶级父键")
    private String dictParentKey;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "字典父键")
    private String dictTopKey;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty("字典类型进一步划分(如质检项目：1: 质量指标检验 2：储存品质检验 3：食品安全检验)")
    private String dictFarType;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "说明")
    private String remark;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT_UPDATE)
    @ApiModelProperty(value = "最后更新者")
    private String updateBy;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT_UPDATE)
    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime updateTime;
}