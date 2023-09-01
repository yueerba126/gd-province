package com.sydata.dashboard.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * @author xy
 * @describe 报表管理-库存归属报表分页参数
 * @date 2023-02-10 14:10
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "报表管理-库存归属报表分页参数")
public class StockAffiliationPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty(value = "地市编码")
    private String city;

    @ApiModelProperty(value = "粮食品种类别")
    private String lspzlb;
}
