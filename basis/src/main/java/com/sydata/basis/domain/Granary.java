package com.sydata.basis.domain;

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
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.IdType.INPUT;

/**
 * 基础信息-廒间信息对象 basis_granary
 *
 * @author lzq
 * @date 2022-07-08
 */
@ApiModel(description = "基础信息-廒间信息")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("basis_granary")
public class Granary extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键ID（廒间代码)仓房代码+3位顺序码")
    @TableId(value = "id", type = INPUT)
    private String id;

    @ApiModelProperty("廒间代码")
    private String ajdh;

    @ApiModelProperty("廒间名称")
    private String ajmc;

    @ApiModelProperty("仓房(或油罐)编码")
    private String cfbh;

    @ApiModelProperty("廒间长度")
    private BigDecimal ajcd;

    @ApiModelProperty("廒间宽度")
    private BigDecimal ajkd;

    @ApiModelProperty("廒间高度")
    private BigDecimal ajgd;

    @ApiModelProperty("廒间设计仓容")
    private BigDecimal ajsjcr;

    @ApiModelProperty("廒间启用日期")
    private LocalDate ajqyrq;

    @ApiModelProperty("廒间状态")
    private String ajzt;

    @ApiModelProperty("操作标志")
    private String czbz;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime zhgxsj;
}