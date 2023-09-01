package com.sydata.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 质检信息表
 * </p>
 *
 * @author lzq
 * @since 2022-05-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("manage_quality_inspection")
@ApiModel(value = "QualityInspection对象", description = "质检信息表")
public class QualityInspection extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键id 货位代码+质检报告单号")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "质检报告单号:由检验类别+检验日期（yyyyMMdd）+4位顺序号组成")
    private String zjbgdh;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @ApiModelProperty(value = "入库日期")
    private LocalDate rkrq;

    @ApiModelProperty(value = "检验类别")
    private String jylb;

    @ApiModelProperty(value = "检验时间")
    private LocalDateTime jysj;

    @ApiModelProperty(value = "检验单位")
    private String jydw;

    @ApiModelProperty(value = "检验人")
    private String jyr;

    @ApiModelProperty(value = "检验依据")
    private String jyyj;

    @ApiModelProperty(value = "指标类别")
    private String zblb;

    @ApiModelProperty(value = "检验项目")
    private String jyxm;

    @ApiModelProperty(value = "检验项目值")
    private String jyxmz;

    @ApiModelProperty(value = "指标结果判定")
    private String zbjgpd;

    @ApiModelProperty(value = "签发日期")
    private LocalDate qfrq;

    @ApiModelProperty(value = "报告出具时间")
    private LocalDateTime bgcjsj;

    @ApiModelProperty(value = "审核人姓名")
    private String shrxm;

    @ApiModelProperty(value = "扦样单编号")
    private String qydbh;

    @ApiModelProperty(value = "扦样时间")
    private LocalDateTime qysj;

    @ApiModelProperty(value = "扦样区域")
    private String qyqy;

    @ApiModelProperty(value = "扦样人姓名")
    private String qyrxm;

    @ApiModelProperty(value = "监督人姓名")
    private String jdrxm;

    @ApiModelProperty(value = "样品编号")
    private String ypbh;

    @ApiModelProperty(value = "样品数量")
    private BigDecimal ypsl;

    @ApiModelProperty(value = "代表数量")
    private BigDecimal dbsl;

    @ApiModelProperty(value = "样品等级")
    private String ypdj;

    @ApiModelProperty(value = "是否正常存储年限")
    private String sfzcccnx;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;


}
