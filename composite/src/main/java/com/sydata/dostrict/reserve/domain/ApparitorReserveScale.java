package com.sydata.dostrict.reserve.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_NULL;

/**
 * 粮食储备-储备规模
 *
 * @author lzq
 * @date 2022-04-26
 */
@ApiModel(description = "粮食储备-储备规模")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "apparitor_reserve_scale")
public class ApparitorReserveScale extends BaseFiledEntity implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "id(年份-承储企业-粮食品种-粮食性质)")
    private String id;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "关联储备计划id")
    private String cbjhId;

    @ApiModelProperty(value = "年份")
    private String nf;

    @ApiModelProperty(value = "行政区划代码")
    private String xzqhdm;

    @ApiModelProperty(value = "承储企业")
    private String ccqy;

    @ApiModelProperty(value = "粮食品种")
    private String ylpz;

    @ApiModelProperty(value = "粮食性质")
    private String ylxz;

    @ApiModelProperty(value = "储备规模计划数量")
    private BigDecimal ylcbgmjhsl;

    @ApiModelProperty(value = "储备规模计划文号")
    private String cbgmjhwh;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "操作标志（i：新增(默认)，u：更新，d：删除）")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;
}