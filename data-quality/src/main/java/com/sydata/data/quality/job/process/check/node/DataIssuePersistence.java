package com.sydata.data.quality.job.process.check.node;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.quality.DataQualityProcessContext;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.domain.BaseFiledEntity;
import com.sydata.data.quality.domain.DataIssue;
import com.sydata.data.quality.domain.DataIssueDtl;
import com.sydata.data.quality.job.process.check.IIssueCheckProcess;
import com.sydata.data.quality.job.process.check.enums.IssueCheckEnum;
import com.sydata.data.quality.service.IDataIssueDtlService;
import com.sydata.data.quality.service.IDataIssueService;
import com.sydata.framework.util.BeanUtils;
import lombok.SneakyThrows;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

/**
 * @author lzq
 * @description 数据问题持久化
 * @date 2023/4/23 16:32
 */
@Component
public class DataIssuePersistence implements IIssueCheckProcess {

    @Resource
    private IDataIssueService dataIssueService;

    @Resource
    private IDataIssueDtlService dataIssueDtlService;

    @Override
    public IssueCheckEnum node() {
        return IssueCheckEnum.ISSUE_DATA_PERSISTENCE;
    }

    private final ObjectMapper objectMapper;

    public DataIssuePersistence(MappingJackson2HttpMessageConverter messageConverter) {
        this.objectMapper = messageConverter.getObjectMapper();
    }

    @Override
    public void process(DataQualityProcessContext context) {
        Collection<DataIssueDto> values = context.getDataIdIssueDtoMap().values();

        Set<String> issueSaveSuccessIds = StreamEx.of(CollectionUtils.emptyIfNull(values))
                .filter(dto -> CollectionUtils.isNotEmpty(dto.getDtls()))
                .map(dto -> buildDataIssue(dto, context))
                .toListAndThen(dataIssueService::ignoreBatchInsert);

        StreamEx.of(CollectionUtils.emptyIfNull(values))
                .filter(dto -> CollectionUtils.isNotEmpty(dto.getDtls()))
                .filter(dto -> issueSaveSuccessIds.contains(dto.getId()))
                .map(DataIssueDto::getDtls)
                .flatMap(Collection::stream)
                .map(dtl -> BeanUtils.copyByClass(dtl, DataIssueDtl.class))
                .toListAndThen(dataIssueDtlService::saveBatch);

        context.setIssueSaveSuccessIds(issueSaveSuccessIds);
    }

    /**
     * 构建数据问题实体
     *
     * @param dto     数据问题DTO
     * @param context 数据质量处理上下文
     */
    @SneakyThrows(Throwable.class)
    private DataIssue buildDataIssue(DataIssueDto dto, DataQualityProcessContext context) {
        BaseFiledEntity entity = context.getEntityMap().get(dto.getDataId());
        BaseApiParam apiParam = context.getParamMap().get(dto.getDataId());

        return new DataIssue()
                .setId(dto.getId())
                .setApiCode(context.getDataApiEnum().getApiCode())
                .setDataId(dto.getDataId())
                .setDataJson(objectMapper.writeValueAsString(apiParam))
                .setIssueCount(dto.getDtls().size())
                .setRegionId(entity.getRegionId())
                .setCountryId(entity.getCountryId())
                .setProvinceId(entity.getProvinceId())
                .setCityId(entity.getCityId())
                .setAreaId(entity.getAreaId())
                .setEnterpriseId(entity.getEnterpriseId())
                .setStockHouseId(entity.getStockHouseId());
    }
}
