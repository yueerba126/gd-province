package com.sydata.data.quality.job;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.data.quality.job.config.IssueCheckJobConfig;
import com.sydata.data.quality.job.process.check.DataIssueCheckProcessReceive;
import com.sydata.data.quality.job.process.check.IDataIssueCheckClear;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.redisson.Redisson;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.*;


/**
 * @author lzq
 * @description 数据质量问题检查定时任务
 * @date 2023/4/22 18:02
 */
@Slf4j
@Component
public class DataIssueCheckJob {

    private static String countDownLatch = "dataQuality:issueCheckJob:countDownLatch";

    private static String countDownLatchLock = "dataQuality:issueCheckJob:countDownLatchCountLock";

    @Resource
    private DataIssueCheckProcessReceive dataIssueCheckProcessReceive;

    @Resource
    private Redisson redisson;

    @Resource
    private List<IDataIssueCheckClear> clears;

    @Resource
    private DataQualityReportJob dataQualityReportJob;

    @PostConstruct
    public void init() {
        String applicationName = SpringUtil.getApplicationName();
        countDownLatch = applicationName + COLON + countDownLatch;
        countDownLatchLock = applicationName + COLON + countDownLatchLock;
    }

    @XxlJob("dataQualityIssueCheckJob")
    public void dataQualityIssueCheckJob() {
        IssueCheckJobConfig jobConfig = IssueCheckJobConfig.jobConfig();
        List<String> apiCodes = jobConfig.getApiCodes();
        if (CollectionUtil.isEmpty(apiCodes)) {
            XxlJobHelper.log("传参中未找到可以检测的数据标识,无法进行数据质量分析");
            return;
        }
        List<DataApiEnum> dataApiEnums = StreamEx.of(apiCodes).map(DataApiEnum::getByApiCode).nonNull().toList();

        LocalDateTime now = LocalDateTime.now();
        LocalDate endDate = now.toLocalDate();

        int shardTotal = XxlJobHelper.getShardTotal();
        int shardIndex = XxlJobHelper.getShardIndex();
        boolean tryLock = competeCountDownLatch(shardTotal, dataApiEnums);

        StringBuilder logs = new StringBuilder()
                .append(CRLF)
                .append("=====================")
                .append("开始时间").append(now).append(SPACE)
                .append("实例总数").append(shardTotal).append(SPACE)
                .append("实例下标").append(shardIndex).append(SPACE)
                .append("实例总数").append(shardTotal).append(SPACE)
                .append("数据质量拉取截止日期").append(endDate).append(SPACE)
                .append("竞争锁状态" + tryLock).append(SPACE)
                .append("=====================")
                .append(CRLF);

        // 锁的拥有者处理清理工作
        if (tryLock && CollectionUtils.isNotEmpty(clears)) {
            LocalDateTime clearNow = LocalDateTime.now();
            clears.forEach(IDataIssueCheckClear::clear);
            logs.append("数据清理耗时：").append(Duration.between(clearNow, LocalDateTime.now()).toMillis())
                    .append("MS").append(CRLF);
        }

        // 数据质量检查
        int totalRows = doDataQualityCheck(dataApiEnums, endDate, logs, jobConfig);

        LocalDateTime end = LocalDateTime.now();
        long timeConsuming = Duration.between(now, end).toMillis();

        logs.append("=====================")
                .append("结束时间").append(end).append(SPACE)
                .append("处理总耗时").append(timeConsuming).append("MS").append(SPACE)
                .append("处理总行数" + totalRows).append(SPACE)
                .append("==============================================================")
                .append(CRLF);
        XxlJobHelper.log(logs.toString());

        // 数据质量报告
        dataQualityReportJob.dataQualityReport(dataApiEnums, shardTotal, shardIndex);
    }

    /**
     * 竞争设置API的countDownLatch控制权
     *
     * @param shardTotal   实例总数
     * @param dataApiEnums 上报数据列表
     * @return 竞争锁状态
     */
    private boolean competeCountDownLatch(int shardTotal, List<DataApiEnum> dataApiEnums) {
        RLock lock = redisson.getLock(countDownLatchLock);

        // 抢到锁的设置countDownLatch和拉取数据的截止时间,未抢到锁的等着拥有者设置完
        try {
            boolean tryLock = lock.tryLock();
            if (!tryLock) {
                lock.lock();
            } else {
                dataApiEnums.forEach(dataApiEnum -> getCountDownLatch(dataApiEnum).trySetCount(shardTotal));
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
     * @param dataApiEnum 数据API枚举
     * @return CountDownLatch
     */
    private RCountDownLatch getCountDownLatch(DataApiEnum dataApiEnum) {
        String apiCodeCountDownLatch = countDownLatch + COLON + dataApiEnum.getApiCode();
        return redisson.getCountDownLatch(apiCodeCountDownLatch);
    }

    /**
     * 数据质量检查
     *
     * @param dataApiEnums 数据API枚举列表
     * @param reportDate   报告日期
     * @param logs         日志
     * @param jobConfig    数据质量定时任务配置
     */
    private int doDataQualityCheck(List<DataApiEnum> dataApiEnums, LocalDate reportDate, StringBuilder logs,
                                   IssueCheckJobConfig jobConfig) {
        int partitionSize = jobConfig.getPartitionSize();
        int threads = jobConfig.getThreads();

        // 循环数据API,一个个接口数据检查（协调多实例）
        AtomicInteger totalRows = new AtomicInteger(0);
        dataApiEnums.forEach(dataApiEnum -> {
            LocalDateTime start = LocalDateTime.now();
            String apiCode = dataApiEnum.getApiCode();

            logs.append("===============").append("接口编号：").append(apiCode).append("===============");

            int rows = 0;
            try {
                RCountDownLatch countDownLatch = getCountDownLatch(dataApiEnum);
                countDownLatch.countDown();
                countDownLatch.await();
                rows = dataIssueCheckProcessReceive.receive(dataApiEnum, reportDate, logs, partitionSize, threads);
            } catch (Exception e) {
                log.error("接口编号{}在数据质量检查过程中发生异常 ", apiCode, e);
                logs.append("异常信息：").append(ExceptionUtil.getRootCauseMessage(e)).append(CRLF);
            } finally {
                LocalDateTime end = LocalDateTime.now();
                long timeConsuming = Duration.between(start, end).toMillis();

                logs.append("=====").append("接口编号：").append(apiCode).append("=====")
                        .append("处理行数：").append(rows).append("=====")
                        .append("处理耗时：").append(timeConsuming).append("MS").append(CRLF);
                totalRows.addAndGet(rows);
            }
        });
        return totalRows.get();
    }
}
