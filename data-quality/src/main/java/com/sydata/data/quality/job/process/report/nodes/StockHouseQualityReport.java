package com.sydata.data.quality.job.process.report.nodes;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.sydata.basis.domain.StockHouse;
import com.sydata.basis.service.IStockHouseService;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.domain.RequestQualityYears;
import com.sydata.collect.service.IRequestQualityYearsService;
import com.sydata.common.domain.BaseFiledEntity;
import com.sydata.data.quality.domain.Report;
import com.sydata.data.quality.domain.ReportDtl;
import com.sydata.data.quality.enums.ReportTargetTypeEnum;
import com.sydata.data.quality.job.dto.QualityReportStatisticsDto;
import com.sydata.data.quality.job.process.report.IQualityReportProcess;
import com.sydata.data.quality.job.process.report.enums.QualityReportNodeEnum;
import com.sydata.data.quality.service.IReportDtlService;
import com.sydata.data.quality.service.IReportService;
import com.sydata.framework.job.FrameworkJobHelper;
import com.sydata.trade.service.IStockGrainNewestService;
import com.sydata.trade.vo.StockGrainStatisticsVo;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.SPACE;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.emptyMap;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

/**
 * @author lzq
 * @description 库区质量报告
 * @date 2023/4/24 18:33
 */
@Slf4j
@Component
public class StockHouseQualityReport implements IQualityReportProcess {

    @Resource
    private IRequestQualityYearsService requestQualityYearsService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private IReportService reportService;

    @Resource
    private IReportDtlService reportDtlService;

    @Resource
    private IStockGrainNewestService stockGrainNewestService;

    @Resource
    private IStockHouseService stockHouseService;


    @Override
    public QualityReportNodeEnum node() {
        return QualityReportNodeEnum.STOCK_HOUSE;
    }

    @Override
    public int generate(LocalDate statisticsDate, LocalDate reportDate, List<DataApiEnum> dataApiEnums, StringBuilder logs) {
        // 查询所库区列表并且根据实例分片数据
        List<StockHouse> list = stockHouseService.list();
        int size = list.size();
        List<StockHouse> stockHouses = FrameworkJobHelper.shardList(list);
        logs.append("库区总数:").append(size).append(SPACE).append("分配数:").append(stockHouses.size()).append(SPACE);
        if (CollectionUtils.isEmpty(stockHouses)) {
            return 0;
        }

        List<Report> reports = new ArrayList<>();
        List<ReportDtl> reportDtls = new ArrayList<>();

        // 查询库区库存统计
        List<String> stockHouseIds = StreamEx.of(stockHouses).map(BaseFiledEntity::getStockHouseId).toList();
        List<StockGrainStatisticsVo> statisticsVos = stockGrainNewestService.reserveStatistics(stockHouseIds);
        Map<String, StockGrainStatisticsVo> stockMap = StreamEx.of(statisticsVos)
                .toMap(StockGrainStatisticsVo::getStockHouseId, identity());

        // 查询库区请求数据质量统计
        List<String> apiCodes = StreamEx.of(dataApiEnums).map(DataApiEnum::getApiCode).toList();
        List<RequestQualityYears> quality = requestQualityYearsService.listByStockHouseIds(stockHouseIds, apiCodes);

        // 根据接口编号转map
        Function<List<RequestQualityYears>, Map<String, RequestQualityYears>> apiCodeFunction = requests ->
                requests.stream().collect(toMap(RequestQualityYears::getApiCode, identity()));

        // 根据库区ID分组
        Map<String, Map<String, RequestQualityYears>> qualityMap = StreamEx.of(quality)
                .groupingBy(RequestQualityYears::getStockHouseId, collectingAndThen(toList(), apiCodeFunction));

        int apiTotalCount = dataApiEnums.size();
        stockHouses.forEach(stockHouse -> {
            String reportId = IdWorker.getIdStr();
            String stockHouseId = stockHouse.getStockHouseId();

            // 构建报告明细列表
            Map<String, RequestQualityYears> requestQualityMap = qualityMap.getOrDefault(stockHouseId, emptyMap());
            List<ReportDtl> dtls = buildReportDtls(stockHouse, reportId, dataApiEnums, requestQualityMap);

            // 构建数据质量-报告
            StockGrainStatisticsVo stock = stockMap.get(stockHouseId);
            Report report = buildReport(stockHouse, reportId, statisticsDate, reportDate, stock, dtls, apiTotalCount);

            // 加入新增列表
            reportDtls.addAll(dtls);
            reports.add(report);
        });


        // 批量保存库区数据质量报告
        reportService.saveBatch(reports);
        reportDtlService.saveBatch(reportDtls);

        // 删除库区统计缓存
        stockHouseStatisticsCacheClear(stockHouseIds);

        return reports.size();
    }

    /**
     * 明细报告
     *
     * @param stockHouse        库区信息
     * @param reportId          报告ID
     * @param apiEnums          数据API枚举列表
     * @param requestQualityMap 请求数据质量年报和接口映射
     * @return 明细报告列表
     */
    private List<ReportDtl> buildReportDtls(StockHouse stockHouse, String reportId, List<DataApiEnum> apiEnums,
                                            Map<String, RequestQualityYears> requestQualityMap) {
        List<ReportDtl> dtls = new ArrayList<>();

        String stockHouseId = stockHouse.getStockHouseId();
        for (DataApiEnum api : apiEnums) {

            // 接口请求数据质量(年报)
            RequestQualityYears requestQualityYears = requestQualityMap.get(api.getApiCode());
            int apiRequestTotalCount = requestQualityYears == null ? 0 : requestQualityYears.getTotal();
            int apiRequestSuccessCount = requestQualityYears == null ? 0 : requestQualityYears.getSuccess();

            // 数据质量统计DTO
            QualityReportStatisticsDto cache = QualityReportStatisticsDto.getCache(api, stockHouseId, redisTemplate);
            ReportDtl reportDtl = new ReportDtl()
                    .setReportId(reportId)
                    .setApiCode(api.getApiCode())
                    .setUnicom(apiRequestSuccessCount > 0)
                    .setApiRequestTotalCount(apiRequestTotalCount)
                    .setApiRequestSuccessCount(apiRequestSuccessCount)
                    .setDataTotalCount(cache.getDataTotalCount())
                    .setDataGoodCount(cache.getDataGoodCount())
                    .setDataIssueCount(cache.getDataIssueCount())
                    .setIssueTotalCount(cache.getIssueTotalCount())
                    .setFieldTotalCount(cache.getFieldTotalCount())
                    .setFieldValidCount(cache.getFieldValidCount());

            // 报告明细计算：通过率、完整率、合格率
            reportDtlCalculationRate(reportDtl);

            dtls.add(reportDtl);
        }
        return dtls;
    }

    /**
     * 构建报告
     *
     * @param stockHouse     库区
     * @param reportId       报告ID
     * @param statisticsDate 统计日期
     * @param reportDate     报告日期
     * @param stock          库区库存统计VO
     * @param dtls           报告明细列表
     * @param apiTotalCount  接口总数
     * @return 数据质量-报告
     */
    private Report buildReport(StockHouse stockHouse, String reportId,
                               LocalDate statisticsDate, LocalDate reportDate,
                               StockGrainStatisticsVo stock, List<ReportDtl> dtls, int apiTotalCount) {
        BigDecimal actualStock = Optional.ofNullable(stock).map(StockGrainStatisticsVo::getSjsl).orElse(ZERO);
        BigDecimal valuationStock = Optional.ofNullable(stock).map(StockGrainStatisticsVo::getJjsl).orElse(ZERO);

        // 构建报告
        Report report = new Report()
                .setId(reportId)
                .setStatisticsDate(statisticsDate)
                .setReportDate(reportDate)
                .setTargetType(ReportTargetTypeEnum.STOCK_HOUSE.getType())
                .setTargetId(stockHouse.getStockHouseId())
                .setTargetName(stockHouse.getKqmc())
                .setActualStock(actualStock)
                .setValuationStock(valuationStock)
                .setApiVersion(API_VERSION)
                .setApiTotalCount(apiTotalCount)
                .setApiUnicomCount((int) StreamEx.of(dtls).filter(ReportDtl::getUnicom).count())
                .setApiRequestTotalCount(dtlsSum(dtls, ReportDtl::getApiRequestTotalCount))
                .setApiRequestSuccessCount(dtlsSum(dtls, ReportDtl::getApiRequestSuccessCount))
                .setDataTotalCount(dtlsSum(dtls, ReportDtl::getDataTotalCount))
                .setDataGoodCount(dtlsSum(dtls, ReportDtl::getDataGoodCount))
                .setDataIssueCount(dtlsSum(dtls, ReportDtl::getDataIssueCount))
                .setIssueTotalCount(dtlsSum(dtls, ReportDtl::getIssueTotalCount))
                .setFieldTotalCount(dtlsSum(dtls, ReportDtl::getFieldTotalCount))
                .setFieldValidCount(dtlsSum(dtls, ReportDtl::getFieldValidCount))
                .setRegionId(stockHouse.getRegionId())
                .setCountryId(stockHouse.getCountryId())
                .setProvinceId(stockHouse.getProvinceId())
                .setCityId(stockHouse.getCityId())
                .setAreaId(stockHouse.getAreaId())
                .setEnterpriseId(stockHouse.getEnterpriseId())
                .setStockHouseId(stockHouse.getStockHouseId());

        // 报告计算：实际业务相符率、修正业务相符率、开通率、通过率、完整率、合格率、分数
        reportCalculationRate(report);

        return report;
    }

    /**
     * 库区统计缓存清理
     *
     * @param stockHouseIds 库区ID列表
     */
    private void stockHouseStatisticsCacheClear(List<String> stockHouseIds) {
        DataApiEnum[] values = DataApiEnum.values();
        for (DataApiEnum api : values) {
            QualityReportStatisticsDto.delete(api, stockHouseIds, redisTemplate);
        }
    }
}
