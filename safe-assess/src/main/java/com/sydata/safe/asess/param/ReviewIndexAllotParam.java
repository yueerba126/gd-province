package com.sydata.safe.asess.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author lzq
 * @description 考核评审指标分配参数
 * @date 2023/4/3 17:05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "考核评审指标分配参数")
public class ReviewIndexAllotParam implements Serializable {

    @NotBlank(message = "考核模板指标ID不能为空")
    @ApiModelProperty(value = "考核模板指标ID")
    private String templateIndexId;

    @ApiModelProperty(value = "分配牵头部门ID")
    private Long allotLeadDeptId;

    @ApiModelProperty(value = "分配配合部门IDS")
    private String allotCooperateDeptIds;
}
