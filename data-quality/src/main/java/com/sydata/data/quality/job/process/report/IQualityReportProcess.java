package com.sydata.data.quality.job.process.report;

import cn.hutool.core.util.NumberUtil;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.data.quality.domain.Report;
import com.sydata.data.quality.domain.ReportDtl;
import com.sydata.data.quality.job.process.report.enums.QualityReportNodeEnum;
import one.util.streamex.StreamEx;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.ToIntFunction;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

/**
 * @author lzq
 * @description 数据质量报告生成处理接口
 * @date 2023/5/8 14:42
 */
public interface IQualityReportProcess {

    String API_VERSION = "V2022";


    /**
     * 获取数据质量报告节点枚举
     *
     * @return 数据质量报告节点枚举
     */
    QualityReportNodeEnum node();

    /**
     * 生成报告
     *
     * @param statisticsDate 统计日期
     * @param reportDate     报告日期
     * @param dataApiEnums   数据API枚举列表
     * @param logs           日志
     * @return 生成报告数
     */
    int generate(LocalDate statisticsDate, LocalDate reportDate, List<DataApiEnum> dataApiEnums, StringBuilder logs);


    /**
     * 报告明细统计
     *
     * @param reportDtls 明细列表
     * @param intField   int类型的字段
     * @return 统计总数
     */
    default Integer dtlsSum(List<ReportDtl> reportDtls, ToIntFunction<ReportDtl> intField) {
        return StreamEx.of(reportDtls).mapToInt(intField).sum();
    }

    /**
     * 除法
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 除数精确度，如果为负值，取绝对值
     * @return 两个参数的商
     */
    default BigDecimal numberDiv(Number v1, Number v2, int scale) {
        return v1 == null || v2 == null || v2.doubleValue() == 0 ? ZERO : NumberUtil.div(v1, v2, scale, HALF_UP);
    }


    /**
     * 报告明细计算：通过率、完整率、合格率
     *
     * @param reportDtl 数据质量报告明细
     */
    default void reportDtlCalculationRate(ReportDtl reportDtl) {
        BigDecimal passRate = numberDiv(reportDtl.getApiRequestSuccessCount(),
                reportDtl.getApiRequestTotalCount(), 4).movePointRight(2);

        BigDecimal fullRate = numberDiv(reportDtl.getFieldValidCount(), reportDtl.getFieldTotalCount(),
                4).movePointRight(2);

        BigDecimal goodRate = numberDiv(reportDtl.getDataGoodCount(), reportDtl.getDataTotalCount(),
                4).movePointRight(2);

        reportDtl.setPassRate(passRate).setFullRate(fullRate).setGoodRate(goodRate);
    }


    /**
     * 报告计算：实际业务相符率、修正业务相符率、开通率、通过率、完整率、合格率、分数
     *
     * @param report 数据质量报告
     */
    default void reportCalculationRate(Report report) {
        // 实际业务相符率(计价库存/实际库存)
        BigDecimal actualMatchRate = numberDiv(report.getValuationStock(), report.getActualStock(),
                4).movePointRight(2);

        // 修正业务相符率(实际业务相符率 <= 100 ? 实际业务相符率 : 实际业务相符率 <= 200 ? 200 - 实际业务相符率 : 0)
        BigDecimal hundred = BigDecimal.valueOf(100);
        BigDecimal twoHundred = BigDecimal.valueOf(200);
        BigDecimal correctMatchRate = actualMatchRate.compareTo(hundred) <= 0 ? actualMatchRate :
                actualMatchRate.compareTo(twoHundred) <= 0 ? twoHundred.subtract(actualMatchRate) : ZERO;


        // 计算：开通率、通过率、完整率、合格率、分数
        BigDecimal unicomRate = numberDiv(report.getApiUnicomCount(), report.getApiTotalCount(),
                4).movePointRight(2);

        BigDecimal passRate = numberDiv(report.getApiRequestSuccessCount(), report.getApiRequestTotalCount(),
                4).movePointRight(2);

        BigDecimal fullRate = numberDiv(report.getFieldValidCount(), report.getFieldTotalCount(),
                4).movePointRight(2);

        BigDecimal goodRate = numberDiv(report.getDataGoodCount(), report.getDataTotalCount(),
                4).movePointRight(2);

        BigDecimal score = unicomRate.multiply(BigDecimal.valueOf(0.1))
                .add(passRate.multiply(BigDecimal.valueOf(0.1)))
                .add(fullRate.multiply(BigDecimal.valueOf(0.2)))
                .add(goodRate.multiply(BigDecimal.valueOf(0.6)))
                .multiply(correctMatchRate).movePointLeft(2);


        report.setActualMatchRate(actualMatchRate)
                .setCorrectMatchRate(correctMatchRate)
                .setUnicomRate(unicomRate)
                .setPassRate(passRate)
                .setFullRate(fullRate)
                .setGoodRate(goodRate)
                .setScore(score);
    }
}
