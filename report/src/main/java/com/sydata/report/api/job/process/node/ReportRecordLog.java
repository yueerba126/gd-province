package com.sydata.report.api.job.process.node;

import cn.hutool.core.map.MapUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.report.api.job.ReportProcessContext;
import com.sydata.report.api.job.process.enums.ReportProcessEnum;
import com.sydata.report.api.job.process.IReportProcess;
import com.sydata.report.domain.DataHandle;
import com.sydata.report.domain.ReportLogs;
import com.sydata.report.enums.ReportCodeEnum;
import com.sydata.report.service.IReportLogsService;
import com.sydata.report.vo.ReportResultVo;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.sydata.report.api.job.process.enums.ReportProcessEnum.RECORD_LOG;
import static java.lang.Boolean.FALSE;
import static java.util.function.Function.identity;

/**
 * @author lzq
 * @description 数据上报数据处理-记录上报日志
 * @date 2022/11/2 14:50
 */
@Slf4j
@Component
public class ReportRecordLog implements IReportProcess {

    @Resource
    private IReportLogsService reportLogsService;

    private final ObjectMapper objectMapper;

    public ReportRecordLog(MappingJackson2HttpMessageConverter messageConverter) {
        this.objectMapper = messageConverter.getObjectMapper();
    }

    @Override
    public ReportProcessEnum node() {
        return RECORD_LOG;
    }

    @Override
    public void process(ReportProcessContext context) {
        Map<String, BaseApiParam> dataIdParamMap = context.getDataIdParamMap();
        Map<BaseApiParam, ReportResultVo> resultMap = context.getResultMap();
        if (MapUtil.isEmpty(dataIdParamMap) || MapUtil.isEmpty(resultMap)) {
            return;
        }

        String apiCode = context.getDataApiEnum().getApiCode();

        List<DataHandle> dataHandles = context.getDataHandles();
        Map<String, DataHandle> dataIdMap = StreamEx.of(dataHandles).toMap(DataHandle::getDataId, identity());

        List<ReportLogs> logs = new ArrayList<>();
        dataIdParamMap.forEach((dataId, param) -> {
            ReportLogs reportLogs = buildReportLogs(apiCode, param, resultMap.get(param), dataIdMap.get(dataId));
            logs.add(reportLogs);
        });

        reportLogsService.saveBatch(logs);
    }

    /**
     * 构建上报日志
     *
     * @param apiCode    接口编号
     * @param param      请求参数
     * @param result     响应结果
     * @param dataHandle 数据处理
     * @return 上报日志
     */
    private ReportLogs buildReportLogs(String apiCode,
                                       BaseApiParam param,
                                       ReportResultVo result,
                                       DataHandle dataHandle) {
        String resultJson = null;
        String paramJson = null;
        try {
            resultJson = objectMapper.writeValueAsString(result);
            paramJson = objectMapper.writeValueAsString(param);
        } catch (JsonProcessingException e) {
            log.error("上报结果序列化异常", e);
        }

        return new ReportLogs()
                .setApiCode(apiCode)
                .setDataId(dataHandle.getDataId())
                .setCzbz(param.getCzbz())
                .setBeginTime(result.getBeginTime())
                .setEndTime(result.getEndTime())
                .setTimeConsuming(result.getTimeConsuming())
                .setParam(paramJson)
                .setResult(resultJson)
                .setState(ReportCodeEnum.SUCCESS.getCode().equals(result.getCode()))
                .setInvokerBatchId(result.getInvokerBatchId())
                .setHandleState(FALSE)
                .setDelivery(FALSE)
                .setRegionId(dataHandle.getRegionId())
                .setCountryId(dataHandle.getCountryId())
                .setProvinceId(dataHandle.getProvinceId())
                .setCityId(dataHandle.getCityId())
                .setAreaId(dataHandle.getAreaId())
                .setEnterpriseId(dataHandle.getEnterpriseId())
                .setStockHouseId(dataHandle.getStockHouseId());
    }
}
