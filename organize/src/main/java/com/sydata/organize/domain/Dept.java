package com.sydata.organize.domain;

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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;

/**
 * 组织架构-部门对象 org_dept
 *
 * @author lzq
 * @date 2022-11-16
 */
@ApiModel(description = "组织架构-部门")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("org_dept")
public class Dept implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @NotBlank(message = "部门名称必填")
    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    @NotNull(message = "父部门ID必填")
    @ApiModelProperty(value = "父部门ID")
    private Long parentId;

    @ApiModelProperty(value = "父部门ID集合,分隔")
    private String parentIds;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "组织ID")
    private String organizeId;

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