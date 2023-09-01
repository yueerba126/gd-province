package com.sydata.reserve.layout.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.organize.annotation.DataBindRegion;
import com.sydata.reserve.layout.domain.ReserveCompany;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;
/**
 * @author lzq
 * @describe 储备布局地理信息-企业信息VO
 * @date 2022-08-19
 */
@ApiModel(description = "储备布局地理信息-企业信息VO")
@Data
@ToString
@Accessors(chain = true)
public class ReserveCompanyVo extends ReserveCompany implements Serializable {

    @DataBindDict(sourceField = "#dwlx", sourceFieldCombination = "dwlx")
    @ApiModelProperty("单位类型名称")
    private String dwlxName;

    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty("行政区划代码名称")
    private String xzqhdmName;

}
