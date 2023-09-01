package com.sydata.report.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.report.domain.ReportLogs;
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
 * @description 数据上报-上报日志VO
 * @date 2022/11/2 17:01
 */
@ApiModel(description = "数据上报-上报日志VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReportLogsVo extends ReportLogs implements Serializable {

    @DataBindDict(sourceField = "#apiCode", sourceFieldCombination = "apiCode")
    @ApiModelProperty(value = "接口名称")
    private String apiName;

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "组织名称")
    private String enterpriseName;

    @DataBindStockHouse(sourceField = "#stockHouseId")
    @ApiModelProperty(value = "库区名称")
    private String stockHouseName;
}
