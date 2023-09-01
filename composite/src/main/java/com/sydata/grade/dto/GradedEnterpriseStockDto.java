/**
 * @filename:GradedEnterpriseReviewVo 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description:TODO(等级粮库评定管理-企业申报审核Dto)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedEnterpriseReviewVo对象", description = "等级粮库评定管理-企业申报审核Dto")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GradedEnterpriseStockDto implements Serializable {

    private static final long serialVersionUID = 1684750987869L;

    @Excel(name = "企业名称", width = 20)
    @ApiModelProperty(name = "qymc", value = "企业名称")
    private String qymc;

    @Excel(name = "库点名称", width = 20)
    @ApiModelProperty(name = "kqmc", value = "库点名称")
    private String kqmc;

    @Excel(name = "所在区域", width = 20)
    @ApiModelProperty(name = "xzqhdmName", value = "所属区域")
    private String xzqhdmName;

    @Excel(name = "粮库仓(罐)容(吨)", width = 30)
    @ApiModelProperty(name = "lkcr", value = "粮库仓(罐)容(吨)")
    private BigDecimal lkcr;

    @Excel(name = "授牌等级", width = 20)
    @ApiModelProperty(name = "spdjName", value = "授牌")
    private String spdjName;

    @Excel(name = "授牌年份", width = 20)
    @ApiModelProperty(name = "spnf", value = "授牌年份")
    private String spnf;

    @Excel(name = "授牌状态")
    @ApiModelProperty(name = "spztName", value = "授牌状态")
    private String spztName;
}
