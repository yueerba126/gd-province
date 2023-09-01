package com.sydata.data.quality.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.data.quality.domain.DataIssue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author lzq
 * @description 数据质量-数据问题vo
 * @date 2023/4/25 9:40
 */
@ApiModel(description = "数据质量-数据问题vo")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DataIssueVo extends DataIssue implements Serializable {

    @DataBindDict(sourceField = "#apiCode", sourceFieldCombination = "apiCode")
    @ApiModelProperty(value = "接口名称")
    private String apiName;

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String enterpriseName;

    @DataBindStockHouse(sourceField = "#stockHouseId")
    @ApiModelProperty(value = "库区名称")
    private String stockHouseName;

    @ApiModelProperty(value = "数据问题明细Map")
    private Map<String, List<String>> dtlFieldNameMap;
}
