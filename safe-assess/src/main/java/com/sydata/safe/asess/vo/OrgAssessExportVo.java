package com.sydata.safe.asess.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author lzq
 * @description 单位考核导出VO
 * @date 2023/4/7 19:01
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "单位考核导出VO")
public class OrgAssessExportVo implements Serializable {

    @Excel(name = "地区", width = 20, needMerge = true)
    private String regionName;

    @Excel(name = "模板总分", width = 20, needMerge = true)
    private BigDecimal templateScore;

    @Excel(name = "考核总分", width = 20, needMerge = true)
    private BigDecimal assessScore;

    @Excel(name = "抽查总分", width = 20, needMerge = true)
    private BigDecimal checkScore;

    @Excel(name = "最终得分", width = 20, needMerge = true)
    private BigDecimal score;

    @Excel(name = "状态", width = 20, needMerge = true)
    private String state;

    @ExcelCollection(name = "考核指标明细")
    private List<OrgAssessExportDtlVo> dtlVos;
}
