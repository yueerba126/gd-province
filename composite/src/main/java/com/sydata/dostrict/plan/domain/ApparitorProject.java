/**
 * @filename:AdminProjectBean 2023年04月24日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.dostrict.plan.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;

/**
 * @Description:TODO(规划建设-项目管理实体类)
 * @version: V1.0
 * @author: lzq
 */
@ApiModel(value = "AdminProjectBean对象", description = "规划建设-项目管理")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("apparitor_project")
public class ApparitorProject extends BaseFiledEntity implements Serializable {

    private static final long serialVersionUID = 1682333634972L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(name = "id", value = "主键ID(项目代码)")
    private String id;

    @ApiModelProperty(name = "czbz", value = "操作标志（i：新增(默认)，u：更新，d：删除）")
    private String czbz;

    @ApiModelProperty(name = "dwdm", value = "单位代码")
    private String dwdm;

    @ApiModelProperty(name = "kqdm", value = "库区代码")
    private String kqdm;

    @ApiModelProperty(name = "dzyx", value = "电子邮箱")
    private String dzyx;

    @ApiModelProperty(name = "fddbrzzhm", value = "法定代表人证照号码")
    private String fddbrzzhm;

    @ApiModelProperty(name = "fddbrzzlx", value = "法定代表人证照类型")
    private String fddbrzzlx;

    @ApiModelProperty(name = "gdzctz", value = "固定资产投资（万元）")
    private BigDecimal gdzctz;

    @ApiModelProperty(name = "gpzq", value = "股票债券（万元）")
    private BigDecimal gpzq;

    @ApiModelProperty(name = "jsdd", value = "建设地点")
    private String jsdd;

    @ApiModelProperty(name = "jsnr", value = "建设内容及规模")
    private String jsnr;

    @ApiModelProperty(name = "jszt", value = "建设状态；1:已立项未开工、2:建设中，3:已验收，4:已取消")
    private String jszt;

    @ApiModelProperty(name = "lxfs", value = "联系方式")
    private String lxfs;

    @ApiModelProperty(name = "lxr", value = "联系人")
    private String lxr;

    @ApiModelProperty(name = "nf", value = "年份")
    private String nf;

    @ApiModelProperty(name = "njcsj", value = "拟建成时间")
    private LocalDate njcsj;

    @ApiModelProperty(name = "nkgsj", value = "拟开工时间")
    private LocalDate nkgsj;

    @ApiModelProperty(name = "qtzj", value = "其他资金（万元）")
    private BigDecimal qtzj;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(name = "sbrq", value = "申报日期")
    private LocalDate sbrq;

    @ApiModelProperty(name = "sczzj", value = "省财政资金（万元）")
    private BigDecimal sczzj;

    @ApiModelProperty(name = "sczzj01", value = "市财政资金（万元）")
    private BigDecimal sczzj01;

    @ApiModelProperty(name = "spwh", value = "审批文号")
    private String spwh;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(name = "xmdm", value = "项目代码")
    private String xmdm;

    @ApiModelProperty(name = "xmdw", value = "项目 (法人) 单位（统一社会信用代码）")
    private String xmdw;

    @ApiModelProperty(name = "xmdzjd", value = "项目地址经度")
    private BigDecimal xmdzjd;

    @ApiModelProperty(name = "xmlx", value = "项目类型")
    private String xmlx;

    @ApiModelProperty(name = "xmmc", value = "项目名称")
    private String xmmc;

    @ApiModelProperty(name = "xzqhdm", value = "行政区划代码")
    private String xzqhdm;

    @ApiModelProperty(name = "yhdk", value = "银行贷款（万元）")
    private BigDecimal yhdk;

    @ApiModelProperty(name = "zmdzwd", value = "项目地址纬度")
    private BigDecimal zmdzwd;

    @ApiModelProperty(name = "ztz", value = "总投资（万元）")
    private BigDecimal ztz;

    @ApiModelProperty(name = "zyczzj", value = "中央财政资金（万元）")
    private BigDecimal zyczzj;

    @ApiModelProperty(name = "zhgxsj", value = "最后更新时间")
    private LocalDateTime zhgxsj;
}
