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
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;

/**
 * 粮食安全考核-抽查计划对象 safe_assess_plan
 *
 * @author czx
 * @date 2023-02-14
 */
@ApiModel(description = "粮食安全考核-抽查计划")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("safe_assess_check_plan")
public class CheckPlan implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "计划名称")
    private String planName;

    @ApiModelProperty(value = "年度")
    private String planYear;

    @ApiModelProperty(value = "状态：保存、抽查中、已抽查")
    private String planState;

    @ApiModelProperty(value = "计划开始日期")
    private LocalDate startDate;

    @ApiModelProperty(value = "计划结束日期")
    private LocalDate endDate;

    @ApiModelProperty(value = "模板id")
    private String templateId;

    @ApiModelProperty(value = "小组id")
    private String groupId;

    @ApiModelProperty(value = "抽查对象行政区域ids,逗号分割")
    private String checkRegionIds;

    @ApiModelProperty(value = "行政区域总数")
    private Integer regionCount;

    @ApiModelProperty(value = "已完成考核行政区域数量")
    private Integer regionCheckedCount;

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