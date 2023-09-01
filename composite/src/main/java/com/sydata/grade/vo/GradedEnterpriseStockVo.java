/**
 * @filename:GradedEnterpriseStockVo 2023年05月25日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.grade.domain.GradedEnterpriseStock;
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**   
 * @Description:TODO(等级粮库评定管理-企业等级库点Vo)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="GradedEnterpriseStockVo对象", description="等级粮库评定管理-企业等级库点Vo")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class GradedEnterpriseStockVo extends GradedEnterpriseStock implements Serializable {

	private static final long serialVersionUID = 1684995741089L;

    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty(name = "xzqhdmName" , value = "所在区域名称")
    private String xzqhdmName;
    @DataBindDict(sourceField = "#spdj", sourceFieldCombination = "grade_stock")
    @ApiModelProperty(name = "spdjName" , value = "授牌等级名称")
    private String spdjName;
    @DataBindDict(sourceField = "#spzt", sourceFieldCombination = "grade_spzt")
    @ApiModelProperty(name = "spztName" , value = "授牌状态名称")
    private String spztName;
    @DataBindDict(sourceField = "#qydj", sourceFieldCombination = "grade_qydj")
    @ApiModelProperty(name = "qydjName" , value = "企业等级名称")
    private String qydjName;
    @DataBindDict(sourceField = "#sbzt", sourceFieldCombination = "grade_sbzt")
    @ApiModelProperty(name = "sbztName", value = "申报状态名称")
    private String sbztName;
    @DataBindDict(sourceField = "#sbdj", sourceFieldCombination = "grade_stock")
    @ApiModelProperty(name = "sbdjName", value = "申报等级名称")
    private String sbdjName;
}
