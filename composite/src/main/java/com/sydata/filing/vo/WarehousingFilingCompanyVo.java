package com.sydata.filing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.filing.domain.WarehousingFilingCompany;
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.*;
import com.sydata.filing.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.sydata.framework.databind.handle.value.bind.ValueBindStrategy.SEPARATED;

/**   
 * @Description:TODO(仓储备案-仓储企业Vo)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="WarehousingFilingCompanyVo对象", description="仓储备案-仓储企业Vo")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class WarehousingFilingCompanyVo extends WarehousingFilingCompany implements Serializable {

	private static final long serialVersionUID = 1687254203072L;

    @DataBindDict(sourceField = "#dwlx", sourceFieldCombination = "dwlx")
    @ApiModelProperty(name = "dwlxName" , value = "企业类型(字典表dwlx)")
    private String dwlxName;

    @DataBindDict(sourceField = "#dwxz", sourceFieldCombination = "filing_dwxz")
    @ApiModelProperty(name = "dwxzName" , value = "企业性质 0:国有 1:外资 2:民营 3:其它")
    private String dwxzName;

    @DataBindDict(sourceField = "#balx", sourceFieldCombination = "filing_balx")
    @ApiModelProperty(name = "balxName" , value = "备案类型 0:初始备案 1:变更备案 2:注销备案")
    private String balxName;

    @DataBindDict(sourceField = "#bazt", sourceFieldCombination = "filing_bazt")
    @ApiModelProperty(name = "baztName" , value = "备案状态 0:保存 1:待备案 2:已备案 3:审核不通过 4:已注销")
    private String baztName;

    @DataBindDict(sourceField = "#baly", sourceFieldCombination = "filing_baly")
    @ApiModelProperty(name = "balyName" , value = "备案来源(0:库软件,1:粤商局)")
    private String balyName;

    @DataBindDict(sourceField = "#ccywlx", sourceFieldCombination = "filing_ccywlx", valueBindStrategy = SEPARATED)
    @ApiModelProperty(name = "ccywlxName" , value = "仓储业务类型逗号分隔 0:储备 1:收购 2:加工 3:销售 4:运输 5:中转 6:进出口 7:其他")
    private String ccywlxName;

    @DataBindDict(sourceField = "#ccpz", sourceFieldCombination = "filing_ccpz", valueBindStrategy = SEPARATED)
    @ApiModelProperty(name = "ccpzName" , value = "仓储品种逗号分隔 0:小麦 1:玉米 2:稻谷 3:大豆 4:成品粮 5:食用植物油 6:其他")
    private String ccpzName;

    @DataBindDict(sourceField = "#lsptzc", sourceFieldCombination = "filing_lsptzc")
    @ApiModelProperty(name = "lsptzcName" , value = "粮食平台注册(0:未注册,1:已注册) 默认已注册")
    private String lsptzcName;

    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty(name = "xzqhdmName" , value = "经营区域")
    private String xzqhdmName;

}
