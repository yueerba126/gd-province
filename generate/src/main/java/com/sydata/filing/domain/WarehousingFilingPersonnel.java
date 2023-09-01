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
 * @Description:TODO(仓储备案-仓储人员实体类)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value="WarehousingFilingPersonnel对象", description="仓储备案-仓储人员")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("warehousing_filing_personnel")
public class WarehousingFilingPersonnel implements Serializable {

    private static final long serialVersionUID = 1687660852365L;

    @TableId(value = "id",type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "主键ID")
    private String id;

    @ApiModelProperty(name = "companyId" , value = "仓储企业ID")
    private String companyId;

    @ApiModelProperty(name = "xm" , value = "姓名")
    private String xm;

    @ApiModelProperty(name = "sfzhm" , value = "身份证号码")
    private String sfzhm;

    @ApiModelProperty(name = "xl" , value = "学历(字典表xl)")
    private String xl;

    @ApiModelProperty(name = "xb" , value = "性别(字典表xb)")
    private String xb;

    @ApiModelProperty(name = "nl" , value = "年龄")
    private Integer nl;

    @ApiModelProperty(name = "zw" , value = "职务/岗位(法人和主要联系人的自己填)")
    private String zw;

    @ApiModelProperty(name = "cyzw" , value = "从业人员职务/岗位(0:粮油保管员 1:粮油质检员）")
    private String cyzw;

    @ApiModelProperty(name = "cynx" , value = "从事该工作年限")
    private Integer cynx;

    @ApiModelProperty(name = "rylb" , value = "人员类别(0:法人,1:主要联系人,2:从业人员)")
    private String rylb;

    @ApiModelProperty(name = "ryjb" , value = "级别(0:初级工 1:中级工 2:高级工)")
    private String ryjb;

    @ApiModelProperty(name = "zc" , value = "职称")
    private String zc;

    @ApiModelProperty(name = "zyzgzsbh" , value = "职业资格证书编号")
    private String zyzgzsbh;

    @ApiModelProperty(name = "zyzgzsmc" , value = "职业资格证书名称")
    private String zyzgzsmc;

    @ApiModelProperty(name = "fzjg" , value = "发证机关")
    private String fzjg;

    @ApiModelProperty(name = "dzyj" , value = "电子邮件")
    private String dzyj;

    @ApiModelProperty(name = "lxfs" , value = "联系方式")
    private String lxfs;

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
