package com.sydata.filing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.*;
import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.*;
import lombok.experimental.Accessors;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* @Description:TODO(仓储备案-仓储附件ExcelVo)
* @date 2023年06月16日
* @version: V1.0
* @author: lzq
*
*/
@ApiModel(value="WarehousingFilingAttachmentExcelVo对象", description="仓储备案-仓储附件ExcelVo")
@Data
@ToString
@Accessors(chain = true)
public class WarehousingFilingAttachmentExcelVo implements Serializable {

    private static final long serialVersionUID = 1687254203072L;

    @Excel(name = "资料名称")
    @ApiModelProperty(name = "fileName" ,value = "资料名称")
    private String fileName;

    @Excel(name = "扫描件id(省平台文件ID)")
    @ApiModelProperty(name = "fileId" ,value = "扫描件id(省平台文件ID)")
    private String fileId;

    @Excel(name = "扫描件路径(库软件文件路径)")
    @ApiModelProperty(name = "fileUrl" ,value = "扫描件路径(库软件文件路径)")
    private String fileUrl;

    @Excel(name = "仓储企业ID")
    @ApiModelProperty(name = "companyIdName" , value = "仓储企业ID")
    private String companyIdName;

}
