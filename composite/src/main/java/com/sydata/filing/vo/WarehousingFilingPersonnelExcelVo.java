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
* @Description:TODO(仓储备案-仓储人员ExcelVo)
* @date 2023年06月16日
* @version: V1.0
* @author: lzq
*
*/
@ApiModel(value="WarehousingFilingPersonnelExcelVo对象", description="仓储备案-仓储人员ExcelVo")
@Data
@ToString
@Accessors(chain = true)
public class WarehousingFilingPersonnelExcelVo implements Serializable {

    private static final long serialVersionUID = 1687254203072L;

    @Excel(name = "姓名")
    @ApiModelProperty(name = "xm" ,value = "姓名")
    private String xm;

    @Excel(name = "身份证号码")
    @ApiModelProperty(name = "sfzhm" ,value = "身份证号码")
    private String sfzhm;

    @Excel(name = "年龄")
    @ApiModelProperty(name = "nl" ,value = "年龄")
    private Integer nl;

    @Excel(name = "职务/岗位(法人和主要联系人的自己填)")
    @ApiModelProperty(name = "zw" ,value = "职务/岗位(法人和主要联系人的自己填)")
    private String zw;

    @Excel(name = "从事该工作年限")
    @ApiModelProperty(name = "cynx" ,value = "从事该工作年限")
    private Integer cynx;

    @Excel(name = "职称")
    @ApiModelProperty(name = "zc" ,value = "职称")
    private String zc;

    @Excel(name = "职业资格证书编号")
    @ApiModelProperty(name = "zyzgzsbh" ,value = "职业资格证书编号")
    private String zyzgzsbh;

    @Excel(name = "职业资格证书名称")
    @ApiModelProperty(name = "zyzgzsmc" ,value = "职业资格证书名称")
    private String zyzgzsmc;

    @Excel(name = "发证机关")
    @ApiModelProperty(name = "fzjg" ,value = "发证机关")
    private String fzjg;

    @Excel(name = "电子邮件")
    @ApiModelProperty(name = "dzyj" ,value = "电子邮件")
    private String dzyj;

    @Excel(name = "联系方式")
    @ApiModelProperty(name = "lxfs" ,value = "联系方式")
    private String lxfs;

    @Excel(name = "仓储企业ID")
    @ApiModelProperty(name = "companyIdName" , value = "仓储企业ID")
    private String companyIdName;

    @Excel(name = "学历(字典表xl)")
    @ApiModelProperty(name = "xlName" , value = "学历(字典表xl)")
    private String xlName;

    @Excel(name = "性别(字典表xb)")
    @ApiModelProperty(name = "xbName" , value = "性别(字典表xb)")
    private String xbName;

    @Excel(name = "从业人员职务/岗位(0:粮油保管员 1:粮油质检员）")
    @ApiModelProperty(name = "cyzwName" , value = "从业人员职务/岗位(0:粮油保管员 1:粮油质检员）")
    private String cyzwName;

    @Excel(name = "人员类别(0:法人,1:主要联系人,2:从业人员)")
    @ApiModelProperty(name = "rylbName" , value = "人员类别(0:法人,1:主要联系人,2:从业人员)")
    private String rylbName;

    @Excel(name = "级别(0:初级工 1:中级工 2:高级工)")
    @ApiModelProperty(name = "ryjbName" , value = "级别(0:初级工 1:中级工 2:高级工)")
    private String ryjbName;

}
