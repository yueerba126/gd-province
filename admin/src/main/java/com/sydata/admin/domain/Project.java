package com.sydata.admin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 行政管理-项目信息对象 admin_project
 *
 * @author lzq
 * @date 2022-07-25
 */
@ApiModel(description = "行政管理-项目信息")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("admin_project")
public class Project extends BaseFiledEntity implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "id(项目代码)")
    private String id;

    @ApiModelProperty(value = "项目代码")
    private String xmdm;

    @ApiModelProperty(value = "项目名称")
    private String xmmc;

    @ApiModelProperty(value = "行政区划代码")
    private String xzqhdm;

    @ApiModelProperty(value = "年份")
    private String nf;

    @ApiModelProperty(value = "项目类型")
    private String xmlx;

    @ApiModelProperty(value = "建设内容及规模")
    private String jsnr;

    @ApiModelProperty(value = "拟开工时间")
    private LocalDate nkgsj;

    @ApiModelProperty(value = "拟建成时间")
    private LocalDate njcsj;

    @ApiModelProperty(value = "建设状态；1:已立项未开工、2:建设中，3:已验收，4:已取消")
    private String jszt;

    @ApiModelProperty(value = "申报日期")
    private LocalDate sbrq;

    @ApiModelProperty(value = "审批文号")
    private String spwh;

    @ApiModelProperty(value = "项目 (法人) 单位（统一社会信用代码）")
    private String xmdw;

    @ApiModelProperty(value = "法定代表人证照类型")
    private String fddbrzzlx;

    @ApiModelProperty(value = "法定代表人证照号码")
    private String fddbrzzhm;

    @ApiModelProperty(value = "联系人")
    private String lxr;

    @ApiModelProperty(value = "联系方式")
    private String lxfs;

    @ApiModelProperty(value = "电子邮箱")
    private String dzyx;

    @ApiModelProperty(value = "建设地点")
    private String jsdd;

    @ApiModelProperty(value = "总投资（万元）")
    private BigDecimal ztz;

    @ApiModelProperty(value = "固定资产投资（万元）")
    private BigDecimal gdzctz;

    @ApiModelProperty(value = "中央财政资金（万元）")
    private BigDecimal zyczzj;

    @ApiModelProperty(value = "省财政资金（万元）")
    private BigDecimal sczzj;

    @ApiModelProperty(value = "市财政资金（万元）")
    private BigDecimal sczzj01;

    @ApiModelProperty(value = "银行贷款（万元）")
    private BigDecimal yhdk;

    @ApiModelProperty(value = "股票债券（万元）")
    private BigDecimal gpzq;

    @ApiModelProperty(value = "其他资金（万元）")
    private BigDecimal qtzj;

    @ApiModelProperty(value = "项目地址经度")
    private BigDecimal xmdzjd;

    @ApiModelProperty(value = "项目地址纬度")
    private BigDecimal zmdzwd;

    @ApiModelProperty(value = "操作标志（i：新增(默认)，u：更新，d：删除）")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;
}