package com.sydata.dashboard.job;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.CRLF;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.SPACE;

/**
 * @author lzq
 * @description 报表统计定时任务
 * @date 2023/5/5 21:17
 */
@Slf4j
@Component
public class DashboardStatisticsJob {

    @Resource
    private List<IDashboardStatistics> list;

    @XxlJob("dashboardStatisticsJob")
    public void dashboardStatisticsJob() {
        LocalDateTime now = LocalDateTime.now();
        StringBuilder logs = new StringBuilder()
                .append(CRLF)
                .append("=====================")
                .append("开始时间").append(now).append(SPACE)
                .append("=====================")
                .append(CRLF);


        // 循环调用统计
        list.forEach(v -> {
            String simpleName = AopUtils.getTargetClass(v).getSimpleName();
            logs.append("========").append("统计类名：").append(simpleName).append("========").append(CRLF);

            LocalDateTime start = LocalDateTime.now();

            try {
                v.dashboardStatistics();
            } catch (Throwable e) {
                log.error("统计类名{}在统计过程中发生异常 ", simpleName, e);
                logs.append("异常信息：").append(ExceptionUtil.getRootCauseMessage(e)).append(CRLF);
            } finally {
                long timeConsuming = Duration.between(start, LocalDateTime.now()).toMillis();
                logs.append("====").append("统计耗时：").append(timeConsuming).append("MS").append("====").append(CRLF);
            }
        });

        LocalDateTime end = LocalDateTime.now();
        long timeConsuming = Duration.between(now, end).toMillis();
        logs.append("=====================")
                .append("结束时间").append(end).append(SPACE)
                .append("统计总耗时").append(timeConsuming).append("MS").append(SPACE)
                .append("==============================================================")
                .append(CRLF);
        XxlJobHelper.log(logs.toString());
    }
}
