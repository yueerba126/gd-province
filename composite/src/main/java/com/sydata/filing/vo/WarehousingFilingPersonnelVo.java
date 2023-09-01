package com.sydata.filing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.filing.domain.WarehousingFilingPersonnel;
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.*;
import com.sydata.filing.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDateTime;

/**   
 * @Description:TODO(仓储备案-仓储人员Vo)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="WarehousingFilingPersonnelVo对象", description="仓储备案-仓储人员Vo")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class WarehousingFilingPersonnelVo extends WarehousingFilingPersonnel implements Serializable {

	private static final long serialVersionUID = 1687254203072L;

    @DataBindWarehousingFilingCompany(sourceField = "#companyId")
    @ApiModelProperty(name = "companyName" , value = "仓储企业ID")
    private String companyName;

    @DataBindDict(sourceField = "#xl", sourceFieldCombination = "xl")
    @ApiModelProperty(name = "xlName" , value = "学历(字典表xl)")
    private String xlName;

    @DataBindDict(sourceField = "#xb", sourceFieldCombination = "xb")
    @ApiModelProperty(name = "xbName" , value = "性别(字典表xb)")
    private String xbName;

    @DataBindDict(sourceField = "#cyzw", sourceFieldCombination = "filing_cyzw")
    @ApiModelProperty(name = "cyzwName" , value = "从业人员职务/岗位(0:粮油保管员 1:粮油质检员）")
    private String cyzwName;

    @DataBindDict(sourceField = "#rylb", sourceFieldCombination = "filing_rylb")
    @ApiModelProperty(name = "rylbName" , value = "人员类别(0:法人,1:主要联系人,2:从业人员)")
    private String rylbName;

    @DataBindDict(sourceField = "#ryjb", sourceFieldCombination = "filing_ryjb")
    @ApiModelProperty(name = "ryjbName" , value = "级别(0:初级工 1:中级工 2:高级工)")
    private String ryjbName;

}
