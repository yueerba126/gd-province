package com.sydata.safe.asess.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lzq
 * @description 部门考核指标提交参数
 * @date 2023/2/21 14:45
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "部门考核指标提交参数")
public class OrgAssessDeptIndexSubmitParam implements Serializable {

    @NotBlank(message = "部门考核指标ID不能为空")
    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "自评分数")
    private BigDecimal orgScore;

    @ApiModelProperty(value = "自评说明")
    private String orgIllustrate;

    @ApiModelProperty(value = "减分原因")
    private String minusCause;

    @ApiModelProperty(value = "整改措施")
    private String measure;

    @ApiModelProperty(value = "佐证材料文件IDS")
    private String fileIds;

    @ApiModelProperty(value = "佐证材料文件名称S")
    private String fileNames;

    /**********************以下参数不需要传输******************************/

    @JsonIgnore
    @ApiModelProperty(value = "单位考核指标ID", hidden = true)
    private String orgAssessIndexId;
}
