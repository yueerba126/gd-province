package com.sydata.dashboard.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sydata.dashboard.enums.SortTypeTwoEnums;
import com.sydata.framework.core.jackson.format.BigDecimalFormat;
import com.sydata.framework.core.jackson.serialize.BigDecimalSerialize;
import com.sydata.framework.util.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 品种类别库存统计VO
 *
 * @author fuql
 * @date 2023-02-10 14:10
 */
@ApiModel(description = "品种类别库存统计VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class VarietyStockVo implements Serializable {

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "计价数量万吨")
    private BigDecimal jjsl;

    @ApiModelProperty(value = "国家平台粮食分类")
    private String lspzfl;

    @ApiModelProperty(value = "国家平台粮食分类名称")
    private String lspzflName;

    public String getLspzflName() {
        if (StringUtils.isNotEmpty(lspzfl)) {
            return SortTypeTwoEnums.getLspzflName(lspzfl);
        }
        return lspzflName;
    }

}
