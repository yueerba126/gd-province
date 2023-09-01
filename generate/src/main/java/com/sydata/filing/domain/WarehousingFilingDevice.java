package com.sydata.filing.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import java.time.LocalDateTime;

/**
 * @Description:TODO(仓储备案-仓储设备实体类)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value="WarehousingFilingDevice对象", description="仓储备案-仓储设备")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("warehousing_filing_device")
public class WarehousingFilingDevice implements Serializable {

    private static final long serialVersionUID = 1687660852365L;

    @TableId(value = "id",type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "主键ID")
    private String id;

    @ApiModelProperty(name = "companyId" , value = "仓储企业ID")
    private String companyId;

    @ApiModelProperty(name = "stockId" , value = "仓储库点ID")
    private String stockId;

    @ApiModelProperty(name = "sbmc" , value = "设备名称")
    private String sbmc;

    @ApiModelProperty(name = "sblx" , value = "设备类型")
    private String sblx;

    @ApiModelProperty(name = "sbxh" , value = "设备型号")
    private String sbxh;

    @ApiModelProperty(name = "jldw" , value = "计量单位")
    private String jldw;

    @ApiModelProperty(name = "sbzt" , value = "设备状态")
    private String sbzt;

    @ApiModelProperty(name = "fileId" , value = "设备照片(省平台的文件id)")
    private String fileId;

    @ApiModelProperty(name = "fileName" , value = "设备照片名称")
    private String fileName;

    @ApiModelProperty(name = "fileUrl" , value = "设备照片路径(库软件的文件路径)")
    private String fileUrl;

    @ApiModelProperty(name = "czbz" , value = "操作标志")
    private String czbz;

    @ApiModelProperty(name = "createBy" , value = "创建者")
    private String createBy;

    @ApiModelProperty(name = "createTime" , value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "updateBy" , value = "更新者")
    private String updateBy;

    @ApiModelProperty(name = "updateTime" , value = "修改时间")
    private LocalDateTime updateTime;

}
