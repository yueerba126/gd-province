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
 * @description 粮食安全考核-单位考核设置附件参数
 * @date 2023/2/18 9:42
 */
@ApiModel(description = "粮食安全考核-单位考核设置附件参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrgAssessUploadFileParam implements Serializable {

    @NotBlank(message = "主键ID不能为空")
    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "文件IDS")
    private String fileIds;

    @ApiModelProperty(value = "文件名称S")
    private String fileNames;
}
