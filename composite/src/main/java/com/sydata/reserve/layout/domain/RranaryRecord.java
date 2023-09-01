package com.sydata.reserve.layout.domain;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import static com.baomidou.mybatisplus.annotation.IdType.INPUT;

/**
 * 储备布局地理信息-廒间信息备案
 *
 * @author lzq
 * @date 2022-07-08
 */
@ApiModel(description = "储备布局地理信息-廒间信息备案")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("reserve_layout_granary")
public class RranaryRecord extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键ID（廒间代码)仓房代码+3位顺序码")
    @TableId(value = "id", type = INPUT)
    private String id;

    @ApiModelProperty("廒间代码")
    private String ajdm;

    @ApiModelProperty("廒间名称")
    private String ajmc;

    @ApiModelProperty("仓房(或油罐)代码")
    private String cfhygdm;

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


    @ApiModelProperty("状态 0：保存，1：提交，2：审核")
    private String status;

    @ApiModelProperty("单位代码")
    private String dwdm;

    @ApiModelProperty("库区代码")
    private String kqdm;
}