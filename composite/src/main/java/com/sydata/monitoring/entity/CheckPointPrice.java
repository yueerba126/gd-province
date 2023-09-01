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

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_NULL;

/**
 * <p>
 * 流通检测-监测点价格关联表
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("monitoring_check_point_price")
@ApiModel(value="CheckPointPrice对象", description="流通检测-监测点价格关联表")
public class CheckPointPrice extends BaseFiledEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "价格类型")
    private String priceType;

    @ApiModelProperty(value = "监测点ID")
    private String pointId;

}
