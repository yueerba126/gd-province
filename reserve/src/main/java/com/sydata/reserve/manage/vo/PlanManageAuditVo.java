package com.sydata.reserve.manage.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 轮换计划审核返回值
 *
 * @author fuql
 * @date 2023-05-19 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "轮换计划审核返回值")
public class PlanManageAuditVo extends PlanManageAuditMainVo implements Serializable {

    @ApiModelProperty(value = "轮换计划审核详情表")
    private List<PlanManageAuditDetailVo> detailVos;

}
