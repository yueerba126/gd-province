package com.sydata.warn.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

/**
 * 库存年限告警阈值设置对象 admin_age_threshold
 *
 * @author fuql
 * @date 2023-05-09
 */
@ApiModel(description = "库存年限告警阈值设置")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("admin_age_threshold")
public class AdminAgeThreshold extends BaseFiledEntity implements Serializable {

    @Excel(name = "库存年限告警阈值设置id")
    @ApiModelProperty(value = "库存年限告警阈值设置id")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @Excel(name = "粮食品种代码 字典：food_big_variety")
    @ApiModelProperty(value = "粮食品种代码 字典：food_big_variety")
    private String ylpz;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remarks;

    @Excel(name = "操作标志（i：新增(默认)，u：更新，d：删除）")
    @ApiModelProperty(value = "操作标志（i：新增(默认)，u：更新，d：删除）")
    private String czbz;

    @Excel(name = "最后更新时间")
    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;

    @Excel(name = "创建时间")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @Excel(name = "创建人")
    @ApiModelProperty(value = "创建人")
    private String createBy;

    @Excel(name = "修改时间")
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @Excel(name = "更新人")
    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @Excel(name = "年限值")
    @ApiModelProperty(value = "年限值")
    private Long ageValue;
}