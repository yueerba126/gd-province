package com.sydata.data.quality.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

/**
 * 数据质量-报告对象 data_quality_report
 *
 * @author system
 * @date 2023-04-18
 */
@ApiModel(description = "数据质量-报告")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("data_quality_report")
public class Report implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "统计日期")
    private LocalDate statisticsDate;

    @ApiModelProperty(value = "报告日期")
    private LocalDate reportDate;

    @ApiModelProperty(value = "报告类型(1库区 2单位 3地区 4地市 5省份)")
    private Integer targetType;

    @ApiModelProperty(value = "报告类型具体的ID")
    private String targetId;

    @ApiModelProperty(value = "报告类型具体的名称")
    private String targetName;

    @ApiModelProperty(value = "实际库存(公斤)")
    private BigDecimal actualStock;

    @ApiModelProperty(value = "计价库存（公斤）")
    private BigDecimal valuationStock;

    @ApiModelProperty(value = "实际业务相符率(计价库存/实际库存)")
    private BigDecimal actualMatchRate;

    @ApiModelProperty(value = "修正业务相符率(实际业务相符率 <= 100 ? 实际业务相符率 : 实际业务相符率 <= 200 ? 200 - 实际业务相符率 : 0)")
    private BigDecimal correctMatchRate;

    @ApiModelProperty(value = "接口版本")
    private String apiVersion;

    @ApiModelProperty(value = "接口总数")
    private Integer apiTotalCount;

    @ApiModelProperty(value = "接口联通数")
    private Integer apiUnicomCount;

    @ApiModelProperty(value = "上报次数")
    private Integer apiRequestTotalCount;

    @ApiModelProperty(value = "上报成功次数")
    private Integer apiRequestSuccessCount;

    @ApiModelProperty(value = "数据总条数")
    private Integer dataTotalCount;

    @ApiModelProperty(value = "数据合格条数")
    private Integer dataGoodCount;

    @ApiModelProperty(value = "数据问题条数")
    private Integer dataIssueCount;

    @ApiModelProperty(value = "问题总数(一条数据可能存在多个问题)")
    private Integer issueTotalCount;

    @ApiModelProperty(value = "字段总数")
    private Integer fieldTotalCount;

    @ApiModelProperty(value = "字段上传数(不为空的字段数)")
    private Integer fieldValidCount;

    @ApiModelProperty(value = "开通率(已联通接口个数/所有接口个数)")
    private BigDecimal unicomRate;

    @ApiModelProperty(value = "通过率(上报次数/上报成功次数)")
    private BigDecimal passRate;

    @ApiModelProperty(value = "完整率(字段上传数/字段总数)")
    private BigDecimal fullRate;

    @ApiModelProperty(value = "合格率(数据合格条数/数据总条数)")
    private BigDecimal goodRate;

    @ApiModelProperty(value = "分数(修正业务相符率*(开通率*10%+通过率*10%+完整率*20%+合格率*60%))")
    private BigDecimal score;

    @ApiModelProperty(value = "行政区域ID")
    private String regionId;

    @ApiModelProperty(value = "国ID")
    private String countryId;

    @ApiModelProperty(value = "省ID")
    private String provinceId;

    @ApiModelProperty(value = "市ID")
    private String cityId;

    @ApiModelProperty(value = "区ID")
    private String areaId;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @ApiModelProperty(value = "关联库区ids(逗号分隔)")
    private String stockHouseIds;
}