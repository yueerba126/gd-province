package com.sydata.filing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.filing.domain.WarehousingFilingStock;
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.*;
import com.sydata.filing.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**   
 * @Description:TODO(仓储备案-仓储库点Vo)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="WarehousingFilingStockVo对象", description="仓储备案-仓储库点Vo")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class WarehousingFilingStockVo extends WarehousingFilingStock implements Serializable {

	private static final long serialVersionUID = 1687254203072L;

    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty(name = "xzqhdmName" , value = "所属区域")
    private String xzqhdmName;

    @DataBindDict(sourceField = "#zyjhys", sourceFieldCombination = "filing_yw")
    @ApiModelProperty(name = "zyjhysName" , value = "有专用检验化验室 0:有 1:无")
    private String zyjhysName;

    @DataBindDict(sourceField = "#lycgjynl", sourceFieldCombination = "filing_yw")
    @ApiModelProperty(name = "lycgjynlName" , value = "具有粮油常规检验能力 0:有 1:无")
    private String lycgjynlName;

    @DataBindDict(sourceField = "#lypzjynl", sourceFieldCombination = "filing_yw")
    @ApiModelProperty(name = "lypzjynlName" , value = "具有粮油品质检验能力 0:有 1:无")
    private String lypzjynlName;

    @DataBindDict(sourceField = "#zbywwxy", sourceFieldCombination = "filing_yw")
    @ApiModelProperty(name = "zbywwxyName" , value = "周边有无危险源 0:有 1:无")
    private String zbywwxyName;

    @DataBindDict(sourceField = "#zbywwry", sourceFieldCombination = "filing_yw")
    @ApiModelProperty(name = "zbywwryName" , value = "周边有无污染源 0:有 1:无")
    private String zbywwryName;

}
