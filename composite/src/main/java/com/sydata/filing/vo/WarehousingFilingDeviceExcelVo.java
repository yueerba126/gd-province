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
* @Description:TODO(仓储备案-仓储设备ExcelVo)
* @date 2023年06月16日
* @version: V1.0
* @author: lzq
*
*/
@ApiModel(value="WarehousingFilingDeviceExcelVo对象", description="仓储备案-仓储设备ExcelVo")
@Data
@ToString
@Accessors(chain = true)
public class WarehousingFilingDeviceExcelVo implements Serializable {

    private static final long serialVersionUID = 1687254203072L;

    @Excel(name = "设备名称")
    @ApiModelProperty(name = "sbmc" ,value = "设备名称")
    private String sbmc;

    @Excel(name = "设备类型")
    @ApiModelProperty(name = "sblx" ,value = "设备类型")
    private String sblx;

    @Excel(name = "设备型号")
    @ApiModelProperty(name = "sbxh" ,value = "设备型号")
    private String sbxh;

    @Excel(name = "计量单位")
    @ApiModelProperty(name = "jldw" ,value = "计量单位")
    private String jldw;

    @Excel(name = "设备状态")
    @ApiModelProperty(name = "sbzt" ,value = "设备状态")
    private String sbzt;

    @Excel(name = "设备照片(省平台的文件id)")
    @ApiModelProperty(name = "fileId" ,value = "设备照片(省平台的文件id)")
    private String fileId;

    @Excel(name = "设备照片名称")
    @ApiModelProperty(name = "fileName" ,value = "设备照片名称")
    private String fileName;

    @Excel(name = "设备照片路径(库软件的文件路径)")
    @ApiModelProperty(name = "fileUrl" ,value = "设备照片路径(库软件的文件路径)")
    private String fileUrl;

    @Excel(name = "仓储企业ID")
    @ApiModelProperty(name = "companyIdName" , value = "仓储企业ID")
    private String companyIdName;

    @Excel(name = "仓储库点ID")
    @ApiModelProperty(name = "stockIdName" , value = "仓储库点ID")
    private String stockIdName;

}
