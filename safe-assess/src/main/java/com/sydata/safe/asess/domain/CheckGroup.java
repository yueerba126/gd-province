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
 * 粮安-实地抽查小组管理对象 safe_assess_check_group
 *
 * @author system
 * @date 2023-02-10
 */
@ApiModel(description = "粮安-实地抽查小组管理")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("safe_assess_check_group")
public class CheckGroup implements Serializable {

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "小组名称")
    private String groupName;

    @ApiModelProperty(value = "组长用户id")
    private String leaderId;

    @ApiModelProperty(value = "联络员用户id")
    private String liaisonManId;

    @ApiModelProperty(value = "组员数")
    private Long groupNum;

    @ApiModelProperty(value = "备注")
    private String remark;

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

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "行政区域ID")
    private String regionId;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "国ID")
    private String countryId;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "省ID")
    private String provinceId;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "市ID")
    private String cityId;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "区ID")
    private String areaId;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "组织ID")
    private String organizeId;
}