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
 * @Description:TODO(仓储备案-仓储附件实体类)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value="WarehousingFilingAttachment对象", description="仓储备案-仓储附件")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("warehousing_filing_attachment")
public class WarehousingFilingAttachment implements Serializable {

    private static final long serialVersionUID = 1687254203072L;

    @TableId(value = "id",type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "主键ID")
    private String id;

    @ApiModelProperty(name = "companyId" , value = "仓储企业ID")
    private String companyId;

    @ApiModelProperty(name = "fileName" , value = "资料名称")
    private String fileName;

    @ApiModelProperty(name = "fileId" , value = "扫描件id(省平台文件ID)")
    private String fileId;

    @ApiModelProperty(name = "fileUrl" , value = "扫描件路径(库软件文件路径)")
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
