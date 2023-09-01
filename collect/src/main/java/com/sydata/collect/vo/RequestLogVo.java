package com.sydata.collect.vo;

import com.sydata.collect.domain.RequestLog;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.common.organize.annotation.DataBindOrganize;
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
 * @description 数据收集-请求日志VO
 * @date 2022/10/11 17:49
 */
@ApiModel(description = "数据收集-请求日志VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RequestLogVo extends RequestLog implements Serializable {

    @DataBindDict(sourceField = "#apiCode", sourceFieldCombination = "apiCode")
    @ApiModelProperty(value = "接口名称")
    private String apiName;

    @DataBindDict(sourceField = "#apiCode", sourceFieldCombination = "apiCode", dataValue = "#dictFarType")
    @ApiModelProperty(value = "接口类型")
    private String apiType;

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "组织名称")
    private String enterpriseName;

    @DataBindStockHouse(sourceField = "#stockHouseId")
    @ApiModelProperty(value = "库区名称")
    private String stockHouseName;

    @DataBindRegion
    @ApiModelProperty(value = "行政区域名称")
    private String regionName;

}
