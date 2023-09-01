package com.sydata.grade.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author lzq
 * @description 文件存储上传参数
 * @date 2023/2/24 10:52
 */
@ApiModel(description = "文件存储上传参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FileStorageUploadParam implements Serializable {

    @ApiModelProperty(value = "文件")
    private MultipartFile file;

    @NotBlank(message = "组织ID必传")
    @Size(max = 18, message = "组织ID最大18位")
    @ApiModelProperty(value = "组织ID")
    private String organizeId;

    @Size(min = 21, max = 21, message = "库区ID长度只能是21")
    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @ApiModelProperty(value = "等级粮库评定模板ID")
    private String templateId;
}
