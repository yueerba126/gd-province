package com.sydata.warn.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.warn.domain.AdminAgeThreshold;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 库存年限告警阈值设置VO
 *
 * @author fuql
 * @date 2022-08-19
 */
@ApiModel(description = "库存年限告警阈值设置VO")
@Data
@ToString
@Accessors(chain = true)
public class AdminAgeThresholdVo extends AdminAgeThreshold implements Serializable {

    @DataBindDict(sourceFieldCombination = "food_big_variety", sourceField = "#ylpz")
    @ApiModelProperty(value = "粮食品种名称")
    private String ylpzName;

}
