package com.sydata.reserve.layout.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 储备布局地理信息-企业信息表
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("reserve_layout_company")
@ApiModel(value = "reserveLayoutCompany对象", description = "储备布局企业信息表")
@Accessors(chain = true)
public class ReserveCompany extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("单位代码")
    private String dwdm;

    @ApiModelProperty("单位名称")
    private String dwmc;

    @ApiModelProperty("单位类型")
    private String dwlx;

    @ApiModelProperty("注册日期")
    private LocalDate zcrq;

    @ApiModelProperty("注册资本")
    private BigDecimal zczb;

    @ApiModelProperty("资产总额")
    private BigDecimal zcze;

    @ApiModelProperty("法定代表人")
    private String fddbr;

    @ApiModelProperty("法人身份证号")
    private String frsfzh;

    @ApiModelProperty("法人联系方式")
    private String frlxfs;

    @ApiModelProperty("企业联系人")
    private String qylxr;

    @ApiModelProperty("办公电话")
    private String bgdh;

    @ApiModelProperty("注册地址")
    private String zcdz;

    @ApiModelProperty("电子邮箱")
    private String dzyx;

    @ApiModelProperty("企业官方网站地址")
    private String qygfwzdz;

    @ApiModelProperty("传真号码")
    private String czhm;

    @ApiModelProperty("邮政编码")
    private String yzbm;

    @ApiModelProperty("行政区划代码")
    private String xzqhdm;

    @ApiModelProperty("上级单位名称")
    private String sjdwmc;

    @ApiModelProperty("上级单位代码")
    private String sjdwdm;

    @ApiModelProperty("库区数")
    private Integer kqs;

    @ApiModelProperty("仓房数")
    private Integer cfs;

    @ApiModelProperty("油罐数")
    private Integer ygs;

    @ApiModelProperty("经度")
    private BigDecimal jd;

    @ApiModelProperty("纬度")
    private BigDecimal wd;

    @ApiModelProperty("状态 0：保存，1：提交，2：审核")
    private String status;

    @ApiModelProperty("有专用检化验室 1:有,0:无")
    private String yzyjhys;

    @ApiModelProperty("具有粮油常规检验能力 1:有,0:无")
    private String lycgjynl;

    @ApiModelProperty("具有粮油品质检验能力 1:有,0:无")
    private String lypzjynl;

    @ApiModelProperty("周边有无危险源 1:有,0:无")
    private String zbywwxy;

    @ApiModelProperty("周边有无污染源 1:有,0:无")
    private String zbywwry;

    @ApiModelProperty("具有粮油品质检验能力 1:有,0:无")
    private String jylypzjynl;

    @ApiModelProperty("联系人职务")
    private String lxrzw;

    @ApiModelProperty("从业人员总数")
    private Integer cyryzs;

    @ApiModelProperty("其中粮油保管员人数")
    private Integer qzlybgrs;

    @ApiModelProperty("其中粮油质检员人数")
    private Integer qzlyzjyrs;

}
