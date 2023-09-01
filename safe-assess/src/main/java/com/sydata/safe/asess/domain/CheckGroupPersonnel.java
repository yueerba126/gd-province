package com.sydata.safe.asess.domain;

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
 * 实地抽查人员对象 safe_assess_check_group_personnel
 *
 * @author system
 * @date 2023-02-13
 */
@ApiModel(description = "实地抽查人员")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("safe_assess_check_group_personnel")
public class CheckGroupPersonnel implements Serializable {

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "抽查小组id")
    private String checkGroupId;

    @ApiModelProperty(value = "抽查人员名称")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "职务")
    private String job;

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