package com.sydata.monitoring.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_NULL;

/**
 * <p>
 * 流通检测-粮油价格采集明主表
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("monitoring_grain_oil_price")
@ApiModel(value="GrainOilPrice对象", description="流通检测-粮油价格采集明主表")
public class GrainOilPrice extends BaseFiledEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @NotNull(message = "监测点ID不能为空")
    @ApiModelProperty(value = "监测点ID")
    private String pointId;

}
