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

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;

/**
 * 组织架构-菜单对象 org_menu
 *
 * @author lzq
 * @date 2022-06-28
 */
@ApiModel(description = "组织架构-菜单")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("org_menu")
public class Menu implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;

    @ApiModelProperty(value = "菜单类型")
    private String type;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    @ApiModelProperty(value = "权限标识")
    private String permission;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "最后更新者")
    private String updateBy;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime updateTime;
}