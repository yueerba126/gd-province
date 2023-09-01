package com.sydata.collect.api.param.grade;

import com.sydata.collect.api.enums.GradeEscalateEnum;
import com.sydata.collect.api.param.BaseApiParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lzq
 * @description 等级粮库评定信息API操作参数
 * @date 2022/10/20 19:27
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "等级粮库评定API操作参数")
public class GradeApiParam extends BaseApiParam implements Serializable {

    @ApiModelProperty(name = "id", value = "主键ID")
    private String id;

    @ApiModelProperty(name = "createBy", value = "创建者")
    private String createBy;

    @ApiModelProperty(name = "createTime", value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "kqdm", value = "库区代码")
    private String kqdm;

    @ApiModelProperty(name = "lkcr", value = "粮库仓(罐)容(吨)")
    private BigDecimal lkcr;

    @ApiModelProperty(name = "qydj", value = "企业等级（县、市、省、区）")
    private String qydj;

    @ApiModelProperty(name = "qydm", value = "企业代码")
    private String qydm;

    @ApiModelProperty(name = "qymc", value = "企业名称")
    private String qymc;

    @ApiModelProperty(name = "kqmc", value = "库区名称")
    private String kqmc;

    @ApiModelProperty(name = "sbdj", value = "申报等级(1,2,3,4,5)(A,AA,AAA,AAAA,AAAAA)")
    private String sbdj;

    @ApiModelProperty(name = "sbnf", value = "申报年份")
    private String sbnf;

    @ApiModelProperty(name = "sbrq", value = "申报或推荐日期")
    private LocalDate sbrq;

    @ApiModelProperty(name = "sbzt", value = "申报状态")
    private String sbzt;

    @ApiModelProperty(name = "citySdpf", value = "市实地评分（总）")
    private BigDecimal citySdpf;

    @ApiModelProperty(name = "citySdzt", value = "市实地评分状态")
    private String citySdzt;

    @ApiModelProperty(name = "provinceSdpf", value = "省实地评分（总）")
    private BigDecimal provinceSdpf;

    @ApiModelProperty(name = "provinceSdzt", value = "省实地评分状态")
    private String provinceSdzt;

    @ApiModelProperty(name = "spdj", value = "授牌等级(1A-5A)")
    private String spdj;

    @ApiModelProperty(name = "spnf", value = "授牌年份")
    private String spnf;

    @ApiModelProperty(name = "spzt", value = "授牌状态")
    private String spzt;

    @ApiModelProperty(name = "updateBy", value = "更新者")
    private String updateBy;

    @ApiModelProperty(name = "updateTime", value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(name = "xzqhdm", value = "所在区域代码")
    private String xzqhdm;

    @ApiModelProperty(name = "zpf", value = "自评分（总）")
    private BigDecimal zpf;

    @ApiModelProperty(name = "enterpriseId", value = "企业ID")
    private Long enterpriseId;

    @ApiModelProperty(name = "warehouseId", value = "所属库点ID")
    private String warehouseId;

    @ApiModelProperty(name = "gradedEnterpriseSelfAssessmentSaveParams", value = "实地评分保存参数")
    private List<GradedEnterpriseSelfAssessmentSaveParam> gradedEnterpriseSelfAssessmentSaveParams;

    @ApiModelProperty(name = "gradedEnterpriseFlowSaveParams", value = "流程保存参数")
    private List<GradedEnterpriseFlowSaveParam> gradedEnterpriseFlowSaveParams;

    @ApiModelProperty(name = "gradedEnterpriseMaterialSaveParams", value = "证明材料保存参数")
    private List<GradedEnterpriseMaterialSaveParam> gradedEnterpriseMaterialSaveParams;

    @ApiModelProperty(name = "gradedEnterpriseProcessSaveParams", value = "审核详情保存参数")
    private List<GradedEnterpriseProcessSaveParam> gradedEnterpriseProcessSaveParams;

    @Override
    public String buildId() {
        return id;
    }
}
