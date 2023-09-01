package com.sydata.data.quality.job;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.data.quality.job.process.report.IQualityReportProcess;
import com.sydata.data.quality.job.process.report.enums.QualityReportNodeEnum;
import com.xxl.job.core.context.XxlJobHelper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.*;

/**
 * @author lzq
 * @description 数据质量报告
 * @date 2023/4/24 17:34
 * <p>
 */
@Slf4j
@Component
public class DataQualityReportJob {

    private static String countDownLatchLock = "dataQuality:reportJob:countDownLatchCountLock";

    private static String countDownLatch = "dataQuality:reportJob:countDownLatch";

    private final List<IQualityReportProcess> dataQualityProcesses;

    @Resource
    private Redisson redisson;

    public DataQualityReportJob(List<IQualityReportProcess> dataQualityProcesses) {
        this.dataQualityProcesses = dataQualityProcesses;
        this.dataQualityProcesses.sort(Comparator.comparing(process -> process.node().getOrder()));
    }

    @PostConstruct
    public void init() {
        String applicationName = SpringUtil.getApplicationName();
        countDownLatchLock = applicationName + COLON + countDownLatchLock;
        countDownLatch = applicationName + COLON + countDownLatch;
    }

    /**
     * 数据质量报告
     *
     * @param dataApiEnums 数据API枚举列表
     * @param shardTotal   分片总数
     * @param shardIndex   分片实例下标
     */
    public void dataQualityReport(List<DataApiEnum> dataApiEnums, int shardTotal, int shardIndex) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate reportDate = now.toLocalDate();
        LocalDate statisticsDate = reportDate.minusDays(1);
        StringBuilder logs = new StringBuilder()
                .append(CRLF)
                .append("=====================")
                .append("开始时间：").append(now).append(SPACE)
                .append("实例下标：").append(shardIndex).append(SPACE)
                .append("实例总数：").append(shardTotal).append(SPACE)
                .append("报告日期：").append(reportDate).append(SPACE)
                .append("统计日期：").append(statisticsDate).append(SPACE)
                .append("=====================")
                .append(CRLF);

        // 竞争设置API的countDownLatch控制权
        competeCountDownLatch(shardTotal);

        // 生成数据质量报告
        AtomicInteger totalRows = new AtomicInteger(0);
        dataQualityProcesses.forEach(processes -> {
            LocalDateTime start = LocalDateTime.now();
            QualityReportNodeEnum node = processes.node();
            logs.append("数据质量报告节点：").append(node.getMsg()).append(SPACE);

            int rows = 0;
            try {
                RCountDownLatch countDownLatch = getCountDownLatch(node);
                countDownLatch.countDown();
                countDownLatch.await();
                rows = processes.generate(statisticsDate, reportDate, dataApiEnums, logs);
            } catch (Exception e) {
                XxlJobHelper.log("数据质量报告节点{}在生成报告中发生异常 ", node.getMsg(), e);
                log.error("数据质量报告节点{}在生成报告中发生异常 ", node.getMsg(), e);
                logs.append("异常信息：").append(ExceptionUtil.getRootCauseMessage(e)).append(CRLF);
            } finally {
                LocalDateTime end = LocalDateTime.now();
                long timeConsuming = Duration.between(start, end).toMillis();

                logs.append("生成报告数：").append(rows).append(SPACE)
                        .append("处理耗时：").append(timeConsuming).append("MS").append(SPACE)
                        .append(CRLF);
                totalRows.addAndGet(rows);
            }
        });

        // 打印日志
        LocalDateTime end = LocalDateTime.now();
        long timeConsuming = Duration.between(now, end).toMillis();
        logs.append("========================")
                .append("结束时间：").append(end).append(SPACE)
                .append("实例下标：").append(shardIndex).append(SPACE)
                .append("实例总数：").append(shardTotal).append(SPACE)
                .append("处理总耗时：").append(timeConsuming).append("MS").append(SPACE)
                .append("生成报告总数：").append(totalRows.get())
                .append("========================")
                .append(CRLF);
        XxlJobHelper.log(logs.toString());
    }

    /**
     * 竞争设置API的countDownLatch控制权
     *
     * @param shardTotal 实例总数
     * @return 竞争锁状态
     */
    private boolean competeCountDownLatch(int shardTotal) {
        RLock lock = redisson.getLock(countDownLatchLock);

        // 抢到锁的设置countDownLatch和拉取数据的截止时间,未抢到锁的等着拥有者设置完
        try {
            boolean tryLock = lock.tryLock();
            if (!tryLock) {
                lock.lock();
            } else {
                dataQualityProcesses.forEach(processes -> getCountDownLatch(processes.node()).trySetCount(shardTotal));
            }
            return tryLock;
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * 获取CountDownLatch
     *
     * @param nodeEnum 数据质量报告节点枚举
     * @return CountDownLatch
     */
    private RCountDownLatch getCountDownLatch(QualityReportNodeEnum nodeEnum) {
        String nodeCountDownLatch = countDownLatch + COLON + nodeEnum.getOrder();
        return redisson.getCountDownLatch(nodeCountDownLatch);
    }
}
