package com.sydata.report.api.job;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.framework.util.StringUtils;
import com.sydata.report.service.ICommandRecordService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.redisson.Redisson;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.*;

/**
 * @author lzq
 * @description 上报国家平台定时器
 * @date 2022/11/2 11:07
 */
@Slf4j
@Component
public class ReportJob {

    private static String countDownLatch = "reportJob:countDownLatch";

    private static String countDownLatchLock = "reportJob:countDownLatchCountLock";

    private static String pullDataEndTimeKey = "reportJob:pullDataEndTime";

    @Resource
    private ReportProcessReceive reportProcessReceive;

    @Resource
    private Redisson redisson;

    @Resource
    private ICommandRecordService commandRecordService;

    @Resource
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
        String applicationName = SpringUtil.getApplicationName();
        countDownLatch = applicationName + COLON + countDownLatch;
        countDownLatchLock = applicationName + COLON + countDownLatchLock;
        pullDataEndTimeKey = applicationName + COLON + pullDataEndTimeKey;
    }

    @XxlJob("report")
    public void report() {
        // 获取指令ID
        String orderId = commandRecordService.lastOrderId();
        if (StringUtils.isEmpty(orderId)) {
            XxlJobHelper.log("暂无收到国家平台指令ID,无法进行数据上报");
            return;
        }

        // 根据定时任务配置的参数 获取需要上报的数据API
        String[] orders = XxlJobHelper.getJobParam().split(SEMICOLON);
        List<DataApiEnum> dataApiEnums = StreamEx.of(orders).map(DataApiEnum::getByApiCode).nonNull().toList();
        if (CollectionUtil.isEmpty(dataApiEnums)) {
            XxlJobHelper.log("传参中未找到可以上报的数据标识,无法进行数据上报");
            return;
        }

        // 竞争设置API的countDownLatch控制权并设置拉取数据截止时间
        LocalDateTime now = LocalDateTime.now();
        int shardTotal = XxlJobHelper.getShardTotal();
        int shardIndex = XxlJobHelper.getShardIndex();
        boolean tryLock = competeCountDownLatch(shardTotal, dataApiEnums, now);
        String endTimeStr = (String) redisTemplate.opsForValue().get(pullDataEndTimeKey);
        if (StringUtils.isEmpty(endTimeStr)) {
            XxlJobHelper.log("未获取到拉取数据截止时间，无法进行数据上报");
            return;
        }

        LocalDateTime endTime = LocalDateTime.parse(endTimeStr);

        StringBuilder logs = new StringBuilder()
                .append(CRLF)
                .append("=====================")
                .append("开始时间").append(now).append(SPACE)
                .append("实例总数").append(shardTotal).append(SPACE)
                .append("下标实例").append(shardIndex).append(SPACE)
                .append("上报处理数据拉取截止时间").append(endTime).append(SPACE)
                .append("竞争锁状态" + tryLock).append(SPACE)
                .append("=====================")
                .append(CRLF);

        // 开始上报
        int totalRows = doReport(dataApiEnums, logs, endTime);
        if (tryLock) {
            redisTemplate.delete(pullDataEndTimeKey);
        }

        LocalDateTime end = LocalDateTime.now();
        long timeConsuming = Duration.between(now, end).toMillis();

        logs.append("=====================")
                .append("结束时间").append(end).append(SPACE)
                .append("处理总耗时").append(timeConsuming).append("MS").append(SPACE)
                .append("处理总行数" + totalRows).append(SPACE)
                .append("=====================")
                .append(CRLF);
        XxlJobHelper.log(logs.toString());
    }

    /**
     * 竞争设置API的countDownLatch控制权
     *
     * @param shardTotal   实例总数
     * @param dataApiEnums 上报数据列表
     * @param now          时间
     * @return 竞争锁状态
     */
    private boolean competeCountDownLatch(int shardTotal, List<DataApiEnum> dataApiEnums, LocalDateTime now) {
        RLock lock = redisson.getLock(countDownLatchLock);

        // 抢到锁的设置countDownLatch和拉取数据的截止时间,未抢到锁的等着拥有者设置完
        try {
            boolean tryLock = lock.tryLock();
            if (!tryLock) {
                lock.lock();
            } else {
                dataApiEnums.forEach(dataApiEnum -> getCountDownLatch(dataApiEnum).trySetCount(shardTotal));

                // 设置拉取数据的截止时间
                redisTemplate.opsForValue().set(pullDataEndTimeKey, now);
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
     * 开始上报
     *
     * @param dataApiEnums 数据API枚举列表
     * @param logs         上下文日志
     * @param endTime      拉取数据的截止时间
     * @return 上报处理行数
     */
    private int doReport(List<DataApiEnum> dataApiEnums, StringBuilder logs, LocalDateTime endTime) {
        // 循环数据API,一个个接口数据上报（协调多实例）
        AtomicInteger totalRows = new AtomicInteger(0);

        dataApiEnums.forEach(dataApiEnum -> {
            LocalDateTime start = LocalDateTime.now();
            String apiCode = dataApiEnum.getApiCode();
            logs.append("=====").append("接口编号：").append(apiCode).append("=====").append(CRLF);
            logs.append("开始时间：").append(start).append(CRLF);

            int rows = 0;
            try {
                RCountDownLatch countDownLatch = getCountDownLatch(dataApiEnum);
                countDownLatch.countDown();
                countDownLatch.await();
                rows = reportProcessReceive.receive(dataApiEnum, endTime);
            } catch (Exception e) {
                log.error("接口编号{}在上报过程中发生异常 ", apiCode, e);
                logs.append("异常信息：").append(ExceptionUtil.getRootCauseMessage(e)).append(CRLF);
            } finally {
                LocalDateTime end = LocalDateTime.now();
                long timeConsuming = Duration.between(start, end).toMillis();
                logs.append("处理行数：").append(rows).append(CRLF);
                logs.append("结束时间：").append(end).append(CRLF);
                logs.append("接口总耗时：").append(timeConsuming).append("MS");
                logs.append(CRLF).append("=======================").append(CRLF);
                totalRows.addAndGet(rows);
            }
        });

        return totalRows.get();
    }
}
