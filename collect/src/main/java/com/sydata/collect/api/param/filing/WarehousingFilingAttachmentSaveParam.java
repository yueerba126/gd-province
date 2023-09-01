package com.sydata.collect.api.param.filing;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description:TODO(仓储备案-仓储附件-保存更新参数)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value="WarehousingFilingAttachmentSaveParam对象", description="仓储备案-仓储附件-保存更新参数")
@Data
@ToString
@Accessors(chain = true)
public class WarehousingFilingAttachmentSaveParam implements Serializable {

    private static final long serialVersionUID = 1687254203072L;

    @ApiModelProperty(name = "id" , value = "主键ID" , example = "主键ID")
    private String id;

    @NotBlank(message = "companyId 不容许为空")
    @ApiModelProperty(name = "companyId" , value = "仓储企业ID" , example = "1")
    @Size(min = 0, max = 66, message = "仓储企业ID 长度必须在 0 - 66 之间")
    private String companyId;

    @ApiModelProperty(name = "fileName" , value = "资料名称" , example = "资料名称")
    @Size(min = 0, max = 255, message = "资料名称 长度必须在 0 - 255 之间")
    private String fileName;

    @ApiModelProperty(name = "fileId" , value = "扫描件id(省平台文件ID)" , example = "扫描件id(省平台文件ID)")
    @Size(min = 0, max = 66, message = "扫描件id(省平台文件ID) 长度必须在 0 - 66 之间")
    private String fileId;

    @ApiModelProperty(name = "fileUrl" , value = "扫描件路径(库软件文件路径)" , example = "扫描件路径(库软件文件路径)")
    @Size(min = 0, max = 255, message = "扫描件路径(库软件文件路径) 长度必须在 0 - 255 之间")
    private String fileUrl;

    @ApiModelProperty(name = "czbz" , value = "操作标志" , example = "String" ,hidden = true)
    @Size(min = 0, max = 1, message = "操作标志 长度必须在 0 - 1 之间")
    private String czbz;

    @ApiModelProperty(name = "createBy" , value = "创建者" , example = "String" ,hidden = true)
    @Size(min = 0, max = 50, message = "创建者 长度必须在 0 - 50 之间")
    private String createBy;

    @ApiModelProperty(name = "createTime" , value = "创建时间" , example = "LocalDateTime" ,hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "updateBy" , value = "更新者" , example = "String" ,hidden = true)
    @Size(min = 0, max = 50, message = "更新者 长度必须在 0 - 50 之间")
    private String updateBy;

    @ApiModelProperty(name = "updateTime" , value = "修改时间" , example = "LocalDateTime" ,hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
