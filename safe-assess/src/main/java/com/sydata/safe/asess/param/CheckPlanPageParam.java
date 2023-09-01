package com.sydata.safe.asess.param;

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
 * @author czx
 * @description 抽查计划查询参数
 * @date 2023/2/13 15:12
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "抽查计划查询参数")
public class CheckPlanPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "计划名称")
    private String planName;

    @ApiModelProperty(value = "模板id")
    private String templateId;

    @ApiModelProperty(value = "状态")
    private String planState;

    @ApiModelProperty(value = "年度")
    private String planYear;

    @ApiModelProperty(value = "组织ID")
    private String organizeId;
}
