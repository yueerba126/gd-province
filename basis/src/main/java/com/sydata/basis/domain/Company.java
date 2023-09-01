package com.sydata.basis.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 企业信息表
 * </p>
 *
 * @author leq
 * @since 2022-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("basis_company")
@ApiModel(value = "BasicDataCompany对象", description = "企业信息表")
@Accessors(chain = true)
public class Company extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.INPUT)
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

    @ApiModelProperty("备案类型 0:初次备案 1:变更备案 2:重新备案")
    private String balx;

    @ApiModelProperty("仓储业务类型逗号分隔 0:储备 1:收购 2:加工 3:销售 4:运输 5:中转 6:进出口 7:其他")
    private String ccywlx;

    @ApiModelProperty("仓储品种逗号分隔 0:小麦 1:玉米 2:稻谷 3:大豆 4:成品粮 5:食用植物油 6:其他")
    private String ccpz;

    @ApiModelProperty("备案状态 0:备案待受理 1:已备案 2:变更待受理 3:已变更 4:停业待受理 5:已停业 6:备案审核不通过 7:变更审核不通过 8:停业审核不通过")
    private String bazt;

    @ApiModelProperty("操作标志")
    private String czbz;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime zhgxsj;
}
