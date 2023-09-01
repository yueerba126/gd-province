package com.sydata.admin.vo;

import com.sydata.admin.domain.ReserveScale;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.organize.annotation.DataBindRegion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @describe 行政管理-储备规模VO
 * @date 2022-07-25 16:51
 */
@ApiModel(description = "行政管理-储备规模VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReserveScaleVo extends ReserveScale implements Serializable {

    @DataBindCompany(sourceField = "#ccqy")
    @ApiModelProperty(value = "承储企业名称")
    private String ccqyName;

    @DataBindDict(sourceFieldCombination = "food_variety", sourceField = "#ylpz")
    @ApiModelProperty(value = "粮食品种名称")
    private String ylpzName;

    @DataBindDict(sourceFieldCombination = "food_nature", sourceField = "#ylxz")
    @ApiModelProperty(value = "粮食性质名称")
    private String ylxzName;

    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty(value = "行政区域名称")
    private String regionName;
}
