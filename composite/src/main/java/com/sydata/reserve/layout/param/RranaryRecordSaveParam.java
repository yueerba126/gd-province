package com.sydata.reserve.layout.param;

import com.sydata.reserve.layout.domain.BaseEntity;
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

/**
 * <p>
 * 储备布局地理信息-廒间信息备案备案
 * </p>
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "储备布局地理信息-廒间信息备案新增参数")
public class RranaryRecordSaveParam extends BaseEntity implements Serializable {


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
