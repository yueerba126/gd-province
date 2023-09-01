package com.sydata.admin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 储备规模
 *
 * @author lzq
 * 2022/8/16 11:39
 */
@Data
@TableName(value = "admin_reserve_scale")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReserveScale extends BaseFiledEntity implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "id(年份-承储企业-粮食品种-粮食性质)")
    private String id;

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