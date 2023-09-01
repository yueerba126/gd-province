package com.sydata.data.quality.job.process.check.node;

import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.quality.DataQualityProcessContext;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.domain.BaseFiledEntity;
import com.sydata.data.quality.job.dto.QualityReportStatisticsDto;
import com.sydata.data.quality.job.process.check.IDataIssueCheckClear;
import com.sydata.data.quality.job.process.check.IIssueCheckProcess;
import com.sydata.data.quality.job.process.check.enums.IssueCheckEnum;
import one.util.streamex.StreamEx;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lzq
 * @description 数据质量报告统计
 * @date 2023/4/23 16:35
 */
@Component
public class QualityReportStatistics implements IIssueCheckProcess, IDataIssueCheckClear {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public IssueCheckEnum node() {
        return IssueCheckEnum.QUALITY_REPORT_STATISTICS;
    }

    @Override
    public void process(DataQualityProcessContext context) {
        Map<String, List<BaseFiledEntity>> stockHouseIdMap = StreamEx.of(context.getEntityMap().values())
                .groupingBy(BaseFiledEntity::getStockHouseId);


        Map<String, DataIssueDto> dataIdIssueDtoMap = context.getDataIdIssueDtoMap();
        Map<String, BaseApiParam> paramMap = context.getParamMap();
        Set<String> issueSaveSuccessIds = context.getIssueSaveSuccessIds();

        stockHouseIdMap.forEach((stockHouseId, list) -> {
            int dataTotalCount = list.size();

            int dataIssueCount = (int) StreamEx.of(list)
                    .map(BaseFiledEntity::getId)
                    .map(dataIdIssueDtoMap::get)
                    .map(DataIssueDto::getId)
                    .filter(issueSaveSuccessIds::contains)
                    .count();

            int dataGoodCount = dataTotalCount - dataIssueCount;

            int issueTotalCount = StreamEx.of(list)
                    .map(BaseFiledEntity::getId)
                    .map(dataIdIssueDtoMap::get)
                    .filter(dto -> issueSaveSuccessIds.contains(dto.getId()))
                    .mapToInt(dto -> dto.getDtls().size())
                    .sum();

            int fieldTotalCount = StreamEx.of(list)
                    .map(BaseFiledEntity::getId)
                    .map(paramMap::get)
                    .mapToInt(BaseApiParam::fieldTotalCount)
                    .sum();

            int fieldValidCount = StreamEx.of(list)
                    .map(BaseFiledEntity::getId)
                    .map(paramMap::get)
                    .mapToInt(BaseApiParam::fieldValidCount)
                    .sum();

            new QualityReportStatisticsDto()
                    .setDataTotalCount(dataTotalCount)
                    .setDataGoodCount(dataGoodCount)
                    .setDataIssueCount(dataIssueCount)
                    .setIssueTotalCount(issueTotalCount)
                    .setFieldTotalCount(fieldTotalCount)
                    .setFieldValidCount(fieldValidCount)
                    .updateCache(context.getDataApiEnum(), stockHouseId, redisTemplate);
        });
    }

    @Override
    public void clear() {
        QualityReportStatisticsDto.clear(redisTemplate);
    }
}
