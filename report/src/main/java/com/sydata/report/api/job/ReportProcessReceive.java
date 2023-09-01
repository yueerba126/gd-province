package com.sydata.report.api.job;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.framework.job.FrameworkJobHelper;
import com.sydata.report.api.config.ReportConfig;
import com.sydata.report.api.job.process.IReportProcess;
import com.sydata.report.domain.DataHandle;
import com.sydata.report.service.IDataHandleService;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author lzq
 * @description 上报数据处理接收器
 * @date 2022/11/2 14:16
 */
@Component
public class ReportProcessReceive {

    @Resource
    private IDataHandleService dataHandleService;

    @Resource
    private ReportConfig reportConfig;

    private final List<IReportProcess> reportProcesses;

    public ReportProcessReceive(List<IReportProcess> reportProcesses) {
        this.reportProcesses = reportProcesses;
        this.reportProcesses.sort(Comparator.comparing(process -> process.node().getOrder()));
    }

    /**
     * 接收数据上报处理任务
     *
     * @param dataApiEnum 数据API枚举
     * @param endTime     截止时间点
     * @return 处理数据总数
     */
    public int receive(DataApiEnum dataApiEnum, LocalDateTime endTime) {
        // 拉取截止时间点的数据，而后进行分片
        String apiCode = dataApiEnum.getApiCode();
        List<String> ids = dataHandleService.notHandleIdsByEndTime(apiCode, endTime);
        List<String> shardIds = FrameworkJobHelper.shardList(ids);
        if (CollectionUtils.isEmpty(shardIds)) {
            return BigInteger.ZERO.intValue();
        }

        // 根据最大传输行数分批处理,一批一个事务
        List<List<String>> partitions = ListUtil.partition(shardIds, reportConfig.getMaxRows());
        AtomicInteger rows = new AtomicInteger(0);
        ReportProcessReceive thisBean = SpringUtil.getBean(this.getClass());
        partitions.forEach(partition -> {
            List<DataHandle> dataHandles = dataHandleService.listByIds(partition);
            int updateRows = thisBean.process(dataApiEnum, endTime, dataHandles);
            rows.addAndGet(updateRows);
        });
        return rows.get();
    }


    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public int process(DataApiEnum dataApiEnum, LocalDateTime endTime, List<DataHandle> dataHandles) {
        if (CollectionUtils.isEmpty(dataHandles)) {
            return 0;
        }
        // 申请上下文,传播在责任链
        ReportProcessContext context = ReportProcessContext.apply(dataApiEnum, dataHandles);
        reportProcesses.forEach(p -> p.process(context));

        // 设置数据处理状态为已处理,并返回影响行数
        List<String> dataIds = StreamEx.of(dataHandles).map(DataHandle::getDataId).distinct().toList();
        return dataHandleService.handleByEndTimeAndDataId(dataApiEnum.getApiCode(), endTime, dataIds);
    }
}
