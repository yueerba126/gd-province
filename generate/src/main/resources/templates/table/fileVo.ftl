package ${voUrl};

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* @Description:TODO(文件上传Vo)
*
* @version: V1.0
* @author: lzq
*
*/
@ApiModel(value = "FileVo对象", description = "文件上传Vo")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class FileVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "fileId", value = "附件id")
    private String fileId;

    @ApiModelProperty(name = "fileUrl", value = "附件url")
    private String fileUrl;

    @ApiModelProperty(name = "fileName", value = "附件名称")
    private String fileName;

}