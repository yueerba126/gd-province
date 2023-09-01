package com.sydata.safe.asess.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lzq
 * @description 部门考核指标分配参数
 * @date 2023/2/20 10:54
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "部门考核指标分配参数")
public class OrgAssessDeptIndexDistributionParam implements Serializable {

    @NotNull(message = "部门考核指标ID必传")
    @ApiModelProperty(value = "部门考核指标ID")
    private String id;

    @ApiModelProperty(value = "自评部门ID")
    private Long deptId;
}
