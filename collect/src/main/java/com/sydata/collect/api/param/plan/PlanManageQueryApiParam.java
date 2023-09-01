package com.sydata.collect.api.param.plan;

import com.sydata.collect.api.param.BaseApiParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 轮换计划审核查询参数
 *
 * @author fuql
 * @date 2023-03-31
 */
@ApiModel(description = "轮换计划审核查询参数")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PlanManageQueryApiParam extends BaseApiParam implements Serializable {

    @ApiModelProperty(value = "轮换计划单号")
    private String lhjhdh;

    @Override
    protected String buildId() {
        return lhjhdh;
    }
}
