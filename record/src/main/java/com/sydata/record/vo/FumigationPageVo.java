package com.sydata.record.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.record.domain.Fumigation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @description 熏蒸备案分页VO
 * @date 2022/12/10 9:49
 */
@ApiModel(description = "熏蒸备案分页VO")
@Data
@ToString
@Accessors(chain = true)
public class FumigationPageVo extends Fumigation implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String enterpriseName;

    @DataBindStockHouse(sourceField = "#stockHouseId")
    @ApiModelProperty(value = "库区名称")
    private String stockHouseName;
}
