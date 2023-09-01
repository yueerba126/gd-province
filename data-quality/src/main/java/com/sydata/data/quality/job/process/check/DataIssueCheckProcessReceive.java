package com.sydata.data.quality.job.process.check;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.register.DataRegister;
import com.sydata.collect.api.service.IData;
import com.sydata.collect.quality.DataQualityProcessContext;
import com.sydata.common.domain.BaseFiledEntity;
import com.sydata.data.quality.job.process.check.enums.IssueCheckEnum;
import com.sydata.framework.job.FrameworkJobHelper;
import com.sydata.framework.job.dto.LimitDto;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.CRLF;

/**
 * @author lzq
 * @description 数据质量问题处理接收器
 * @date 2023/4/22 18:50
 */
@Component
public class DataIssueCheckProcessReceive {

    @Resource
    private DataRegister dataRegister;

    private final List<IIssueCheckProcess> dataQualityProcesses;

    public DataIssueCheckProcessReceive(List<IIssueCheckProcess> dataQualityProcesses) {
        this.dataQualityProcesses = dataQualityProcesses;
        this.dataQualityProcesses.sort(Comparator.comparing(process -> process.node().getOrder()));
    }

    /**
     * 接收处理任务
     *
     * @param api           数据API
     * @param reportDate    报表日期
     * @param logs          日志
     * @param partitionSize 区块大小
     * @param threads       线程数
     * @return 处理行数
     */
    public int receive(DataApiEnum api, LocalDate reportDate, StringBuilder logs, int partitionSize, int threads) {
        IData<BaseApiParam, BaseFiledEntity, Object> dataApi = dataRegister.getByDataApi(api);
        LambdaQueryChainWrapper<BaseFiledEntity> wrapper = this.lambdaQuery(dataApi, reportDate);
        Long totalCount = wrapper.count();
        logs.append("统计总数：").append(totalCount).append(CRLF);
        if (totalCount == 0) {
            return 0;
        }

        // 总数分片分页处理
        List<LimitDto> limits = FrameworkJobHelper.shardLimitTotalCount(totalCount, partitionSize);
        AtomicInteger rows = new AtomicInteger(0);

        Map<IssueCheckEnum, Long> nodeMap = MapUtil.newHashMap(dataQualityProcesses.size());
        limits.forEach(limit -> {
            // 查询数据《查询过程中可能会删除数据,导致偏移量变小拉取不到数据 可以直接结束 因为后面没有数据了》
            List<BaseFiledEntity> list = wrapper.last(limit.toString()).list();
            if (CollectionUtils.isEmpty(list)) {
                return;
            }

            // 数据质量责任链处理
            DataQualityProcessContext context = DataQualityProcessContext.apply(api, list, dataApi, threads);
            dataQualityProcesses.forEach(processes -> {
                LocalDateTime start = LocalDateTime.now();
                try {
                    processes.process(context);
                } finally {
                    // 统计每个处理器总耗时
                    LocalDateTime end = LocalDateTime.now();
                    long time = Duration.between(start, end).toMillis();

                    IssueCheckEnum node = processes.node();
                    nodeMap.put(node, nodeMap.getOrDefault(node, 0L) + time);
                }
            });

            // 记录处理总数
            rows.addAndGet(list.size());
        });


        IssueCheckEnum[] values = IssueCheckEnum.values();
        for (IssueCheckEnum node : values) {
            Long time = nodeMap.get(node);
            logs.append("处理器：").append(node.getMsg()).append("耗时：").append(time).append("MS").append(CRLF);
        }

        return rows.get();
    }


    /**
     * 构建lambdaQuery
     *
     * @param dataApi    数据模型服务类
     * @param reportDate 报表日期
     * @return 数据模型服务类对应的lambdaQuery
     */
    private LambdaQueryChainWrapper<BaseFiledEntity> lambdaQuery(IData<BaseApiParam, BaseFiledEntity, Object> dataApi,
                                                                 LocalDate reportDate) {
        return dataApi.lambdaQuery().setEntityClass(dataApi.getEntityClass())
                .lt(BaseFiledEntity::getUpdateTime, reportDate)
                .orderByAsc(BaseFiledEntity::getUpdateTime);
    }
}
