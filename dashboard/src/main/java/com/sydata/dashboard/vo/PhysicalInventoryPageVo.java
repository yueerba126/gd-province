package com.sydata.dashboard.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 储备粮实物库存报表
 *
 * @author fuql
 * @date 2023-02-10 14:10
 */
@ApiModel(description = "储备粮实物库存报表VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PhysicalInventoryPageVo implements Serializable {

    @ApiModelProperty(value = "总计")
    private PhysicalInventoryNewVo inventoryVo;

    @ApiModelProperty(value = "分页数据")
    private List<PhysicalInventoryNewVo> pageVo;
}
