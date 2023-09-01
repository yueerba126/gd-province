package com.sydata.safe.asess.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author lzq
 * @description 部门自评提交参数
 * @date 2023/2/21 14:45
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "部门自评提交参数")
public class OrgAssessReviewSubmitParam implements Serializable {

    @NotBlank(message = "部门自评ID不能为空")
    @ApiModelProperty(value = "主键ID")
    private String id;

    @NotBlank(message = "状态不能为空")
    @ApiModelProperty(value = "状态：待自评、已自评")
    private String state;

    @Valid
    @NotEmpty(message = "自评指标列表不能为空")
    @ApiModelProperty(value = "自评指标列表")
    private List<OrgAssessReviewIndexSubmitParam> list;
}
