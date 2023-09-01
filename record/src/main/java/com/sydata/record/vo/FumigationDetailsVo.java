
package com.sydata.record.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.record.domain.Fumigation;
import com.sydata.record.domain.FumigationPeople;
import com.sydata.record.domain.FumigationWay;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author lzq
 * @description 熏蒸备案详情VO
 * @date 2022/12/10 9:49
 */
@ApiModel(description = "熏蒸备案详情VO")
@Data
@ToString
@Accessors(chain = true)
public class FumigationDetailsVo extends Fumigation implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String enterpriseName;

    @DataBindStockHouse(sourceField = "#stockHouseId")
    @ApiModelProperty(value = "库区名称")
    private String stockHouseName;

    @ApiModelProperty(value = "熏蒸明细列表")
    private List<FumigationDtlVo> dtls;

    @ApiModelProperty(value = "熏蒸人员列表")
    private List<FumigationPeople> peoples;

    @ApiModelProperty(value = "熏蒸方式列表")
    private List<FumigationWay> ways;
}
