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
 * @description 粮食安全考核-抽查计划设置附件参数
 * @date 2023/2/18 9:42
 */
@ApiModel(description = "粮食安全考核-抽查计划设置附件参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CheckPlanUploadFileParam implements Serializable {

    @NotBlank(message = "计划ID不能为空")
    @ApiModelProperty(value = "计划ID")
    private String planId;

    @NotBlank(message = "单位考核ID不能为空")
    @ApiModelProperty(value = "单位考核ID")
    private String orgAssessId;

    @ApiModelProperty(value = "抽查文件IDS")
    private String checkFileIds;

    @ApiModelProperty(value = "抽查文件名称S")
    private String checkFileNames;
}
