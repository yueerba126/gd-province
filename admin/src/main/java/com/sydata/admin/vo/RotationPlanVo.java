package com.sydata.admin.vo;

import com.sydata.admin.domain.RotationPlan;
import com.sydata.common.basis.annotation.DataBindCompany;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 轮换计划VO
 * </p>
 *
 * @author lzq
 * @since 2022-05-06
 */
@ApiModel(description = "行政管理-轮换计划VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RotationPlanVo extends RotationPlan implements Serializable {

    @ApiModelProperty(value = "单位名称")
    @DataBindCompany(sourceField = "#jhxddw")
    private String dwmc;
}
