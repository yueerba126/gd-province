package com.sydata.dostrict.reserve.vo;

import com.sydata.basis.domain.Company;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.organize.annotation.DataBindRegion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static com.sydata.framework.databind.handle.value.bind.ValueBindStrategy.SEPARATED;

/**
 * @author lzq
 * @describe 粮食储备-企业分布信息VO
 * @date 2022-08-19
 */
@ApiModel(description = "粮食储备-企业分布信息VO")
@Data
@ToString
@Accessors(chain = true)
public class CompanyDistributionVo extends Company implements Serializable {

    @DataBindDict(sourceField = "#dwlx", sourceFieldCombination = "dwlx")
    @ApiModelProperty("单位类型名称")
    private String dwlxName;

    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty("行政区划代码名称")
    private String xzqhdmName;

    @DataBindDict(sourceField = "#balx", sourceFieldCombination = "balx")
    @ApiModelProperty("备案类型名称")
    private String balxName;

    @DataBindDict(sourceField = "#ccywlx", sourceFieldCombination = "ccywlx", valueBindStrategy = SEPARATED)
    @ApiModelProperty("仓储业务类型逗号分隔名称")
    private String ccywlxName;

    @DataBindDict(sourceField = "#ccpz", sourceFieldCombination = "ccpz", valueBindStrategy = SEPARATED)
    @ApiModelProperty("仓储品种逗号分隔名称")
    private String ccpzName;

    @DataBindDict(sourceField = "#bazt", sourceFieldCombination = "bazt")
    @ApiModelProperty("备案状态名称")
    private String baztName;
}
