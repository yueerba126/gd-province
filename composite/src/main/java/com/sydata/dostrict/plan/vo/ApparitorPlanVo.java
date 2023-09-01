package com.sydata.dostrict.plan.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.dostrict.plan.domain.ApparitorPlan;
import com.sydata.organize.annotation.DataBindRegion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @program: gd-province-platform
 * @description: 规划建设-仓储设施建设管理
 * @author: lzq
 * @create: 2023-04-24 16:18
 */
@ApiModel(description = "规划建设-仓储设施建设管理VO")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorPlanVo extends ApparitorPlan implements Serializable {

    @DataBindCompany
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindCompany(sourceField = "#auditUnit")
    @ApiModelProperty(name = "auditUnitName" , value = "审计单位")
    private String auditUnitName;

    @DataBindCompany(sourceField = "#constructionUnit")
    @ApiModelProperty(name = "constructionUnitName" , value = "监理单位")
    private String constructionUnitName;

    @DataBindCompany(sourceField = "#designUnit")
    @ApiModelProperty(name = "designUnitName" , value = "设计单位")
    private String designUnitName;

    @DataBindCompany(sourceField = "#tenderingUnit")
    @ApiModelProperty(name = "tenderingUnitName" , value = "招标单位")
    private String tenderingUnitName;

    @DataBindCompany(sourceField = "#thirdPartyEvaluationUnit")
    @ApiModelProperty(name = "thirdPartyEvaluationUnitName" , value = "第三方评测单位")
    private String thirdPartyEvaluationUnitName;

    @DataBindCompany(sourceField = "#winningBidderUnit")
    @ApiModelProperty(name = "winningBidderUnitName" , value = "中标单位")
    private String winningBidderUnitName;

    @DataBindStockHouse
    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty("行政区划代码名称")
    private String xzqhdmName;

    @DataBindRegion(sourceField = "#regionId")
    @ApiModelProperty("区县名称")
    private String regionIdName;

    @DataBindDict(sourceField = "#xmjzCode", sourceFieldCombination = "xmjz_code")
    @ApiModelProperty("项目进展名称")
    private String xmjzName;
}
