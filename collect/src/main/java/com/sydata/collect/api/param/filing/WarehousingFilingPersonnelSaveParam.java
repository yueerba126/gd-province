package com.sydata.collect.api.param.filing;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description:TODO(仓储备案-仓储人员-保存更新参数)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value="WarehousingFilingPersonnelSaveParam对象", description="仓储备案-仓储人员-保存更新参数")
@Data
@ToString
@Accessors(chain = true)
public class WarehousingFilingPersonnelSaveParam implements Serializable {

    private static final long serialVersionUID = 1687254203072L;

    @ApiModelProperty(name = "id" , value = "主键ID" , example = "主键ID")
    private String id;

    @NotBlank(message = "companyId 不容许为空")
    @ApiModelProperty(name = "companyId" , value = "仓储企业ID" , example = "1")
    @Size(min = 0, max = 66, message = "仓储企业ID 长度必须在 0 - 66 之间")
    private String companyId;

    @ApiModelProperty(name = "xm" , value = "姓名" , example = "姓名")
    @Size(min = 0, max = 32, message = "姓名 长度必须在 0 - 32 之间")
    private String xm;

    @ApiModelProperty(name = "sfzhm" , value = "身份证号码" , example = "身份证号码")
    @Size(min = 0, max = 18, message = "身份证号码 长度必须在 0 - 18 之间")
    private String sfzhm;

    @ApiModelProperty(name = "xl" , value = "学历(字典表xl)" , example = "1")
    @Size(min = 0, max = 1, message = "学历(字典表xl) 长度必须在 0 - 1 之间")
    private String xl;

    @ApiModelProperty(name = "xb" , value = "性别(字典表xb)" , example = "1")
    @Size(min = 0, max = 1, message = "性别(字典表xb) 长度必须在 0 - 1 之间")
    private String xb;

    @ApiModelProperty(name = "nl" , value = "年龄" , example = "1")
    @Digits(integer = 3, fraction = 0, message = "年龄格式不正确")
    private Integer nl;

    @ApiModelProperty(name = "zw" , value = "职务/岗位(法人和主要联系人的自己填)" , example = "职务/岗位(法人和主要联系人的自己填)")
    @Size(min = 0, max = 100, message = "职务/岗位(法人和主要联系人的自己填) 长度必须在 0 - 100 之间")
    private String zw;

    @ApiModelProperty(name = "cyzw" , value = "从业人员职务/岗位(0:粮油保管员 1:粮油质检员）" , example = "1")
    @Size(min = 0, max = 1, message = "从业人员职务/岗位(0:粮油保管员 1:粮油质检员） 长度必须在 0 - 1 之间")
    private String cyzw;

    @ApiModelProperty(name = "cynx" , value = "从事该工作年限" , example = "1")
    @Digits(integer = 3, fraction = 0, message = "从事该工作年限格式不正确")
    private Integer cynx;

    @ApiModelProperty(name = "rylb" , value = "人员类别(0:法人,1:主要联系人,2:从业人员)" , example = "1")
    @Size(min = 0, max = 1, message = "人员类别(0:法人,1:主要联系人,2:从业人员) 长度必须在 0 - 1 之间")
    private String rylb;

    @ApiModelProperty(name = "ryjb" , value = "级别(0:初级工 1:中级工 2:高级工)" , example = "1")
    @Size(min = 0, max = 1, message = "级别(0:初级工 1:中级工 2:高级工) 长度必须在 0 - 1 之间")
    private String ryjb;

    @ApiModelProperty(name = "zc" , value = "职称" , example = "职称")
    @Size(min = 0, max = 100, message = "职称 长度必须在 0 - 100 之间")
    private String zc;

    @ApiModelProperty(name = "zyzgzsbh" , value = "职业资格证书编号" , example = "职业资格证书编号")
    @Size(min = 0, max = 100, message = "职业资格证书编号 长度必须在 0 - 100 之间")
    private String zyzgzsbh;

    @ApiModelProperty(name = "zyzgzsmc" , value = "职业资格证书名称" , example = "职业资格证书名称")
    @Size(min = 0, max = 100, message = "职业资格证书名称 长度必须在 0 - 100 之间")
    private String zyzgzsmc;

    @ApiModelProperty(name = "fzjg" , value = "发证机关" , example = "发证机关")
    @Size(min = 0, max = 100, message = "发证机关 长度必须在 0 - 100 之间")
    private String fzjg;

    @ApiModelProperty(name = "dzyj" , value = "电子邮件" , example = "电子邮件")
    @Size(min = 0, max = 100, message = "电子邮件 长度必须在 0 - 100 之间")
    private String dzyj;

    @ApiModelProperty(name = "lxfs" , value = "联系方式" , example = "联系方式")
    @Size(min = 0, max = 100, message = "联系方式 长度必须在 0 - 100 之间")
    private String lxfs;

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
