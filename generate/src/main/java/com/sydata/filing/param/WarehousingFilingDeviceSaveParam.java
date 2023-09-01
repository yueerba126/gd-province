package com.sydata.filing.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.common.basis.annotation.DataBindDict;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import javax.validation.constraints.Size;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description:TODO(仓储备案-仓储设备-保存更新参数)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value="WarehousingFilingDeviceSaveParam对象", description="仓储备案-仓储设备-保存更新参数")
@Data
@ToString
@Accessors(chain = true)
public class WarehousingFilingDeviceSaveParam implements Serializable {

    private static final long serialVersionUID = 1687660852365L;

    @ApiModelProperty(name = "id" , value = "主键ID" , example = "主键ID")
    private String id;

    @NotBlank(message = "companyId 不容许为空")
    @ApiModelProperty(name = "companyId" , value = "仓储企业ID" , example = "1")
    @Size(min = 0, max = 66, message = "仓储企业ID 长度必须在 0 - 66 之间")
    private String companyId;

    @NotBlank(message = "stockId 不容许为空")
    @ApiModelProperty(name = "stockId" , value = "仓储库点ID" , example = "1")
    @Size(min = 0, max = 66, message = "仓储库点ID 长度必须在 0 - 66 之间")
    private String stockId;

    @ApiModelProperty(name = "sbmc" , value = "设备名称" , example = "设备名称")
    @Size(min = 0, max = 20, message = "设备名称 长度必须在 0 - 20 之间")
    private String sbmc;

    @ApiModelProperty(name = "sblx" , value = "设备类型" , example = "设备类型")
    @Size(min = 0, max = 20, message = "设备类型 长度必须在 0 - 20 之间")
    private String sblx;

    @ApiModelProperty(name = "sbxh" , value = "设备型号" , example = "设备型号")
    @Size(min = 0, max = 50, message = "设备型号 长度必须在 0 - 50 之间")
    private String sbxh;

    @ApiModelProperty(name = "jldw" , value = "计量单位" , example = "计量单位")
    @Size(min = 0, max = 8, message = "计量单位 长度必须在 0 - 8 之间")
    private String jldw;

    @ApiModelProperty(name = "sbzt" , value = "设备状态" , example = "设备状态")
    @Size(min = 0, max = 50, message = "设备状态 长度必须在 0 - 50 之间")
    private String sbzt;

    @ApiModelProperty(name = "fileId" , value = "设备照片(省平台的文件id)" , example = "设备照片(省平台的文件id)")
    @Size(min = 0, max = 50, message = "设备照片(省平台的文件id) 长度必须在 0 - 50 之间")
    private String fileId;

    @ApiModelProperty(name = "fileName" , value = "设备照片名称" , example = "设备照片名称")
    @Size(min = 0, max = 50, message = "设备照片名称 长度必须在 0 - 50 之间")
    private String fileName;

    @ApiModelProperty(name = "fileUrl" , value = "设备照片路径(库软件的文件路径)" , example = "设备照片路径(库软件的文件路径)")
    @Size(min = 0, max = 50, message = "设备照片路径(库软件的文件路径) 长度必须在 0 - 50 之间")
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
