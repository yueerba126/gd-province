package com.sydata.data.quality.job.process.check.node;

import cn.hutool.extra.validation.ValidationUtil;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.service.IData;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.collect.api.validated.valid.BaseValid;
import com.sydata.collect.quality.DataQualityProcessContext;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.domain.BaseFiledEntity;
import com.sydata.data.quality.job.process.check.IIssueCheckProcess;
import com.sydata.data.quality.job.process.check.enums.IssueCheckEnum;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.databind.utils.DataBindCachePutUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;

import static java.lang.Boolean.FALSE;

/**
 * @author lzq
 * @description 数据校验处理器
 * @date 2023/4/23 15:30
 */
@Slf4j
@Component
public class DataCheck implements IIssueCheckProcess {

    @Resource
    private Executor taskExecutor;

    @Override
    public IssueCheckEnum node() {
        return IssueCheckEnum.DATA_CHECK;
    }


    @SneakyThrows(Throwable.class)
    @Override
    public void process(DataQualityProcessContext context) {
        // 获取参数映射并调用数据绑定,不允许设置缓存
        Map<String, BaseApiParam> paramMap = context.getParamMap();
        try {
            DataBindCachePutUtil.set(FALSE);
            DataBindHandleBootstrap.dataHandConvert(paramMap);
        } finally {
            DataBindCachePutUtil.clear();
        }

        // 初始化任务队列
        CountDownLatch taskTotalCount = new CountDownLatch(paramMap.size());
        BlockingDeque<Task> tasks = new LinkedBlockingDeque<>(paramMap.size());
        paramMap.forEach((dataId, param) -> tasks.add(new Task(dataId, param)));

        // 争抢执行任务
        String apiCode = context.getDataApiEnum().getApiCode();

        IData<BaseApiParam, BaseFiledEntity, Object> dataApi = context.getDataApi();
        Runnable execute = () -> {
            Task task;
            while ((task = tasks.poll()) != null) {
                try {
                    BaseApiParam param = task.getApiParam();
                    BaseValid.set(param);

                    // 参数validated校验
                    DataIssueDto dataIssueDto = context.issueRecord(task.getDataId());
                    dataIssueDto.append(ValidationUtil.warpValidate(param, BasicCheck.class, CorrectCheck.class));

                    // 参数自定义实现校验
                    dataApi.validated(dataIssueDto, param);
                } catch (Throwable e) {
                    log.error("接口API={} 数据ID={} 校验发生异常", apiCode, task.getDataId(), e);
                } finally {
                    taskTotalCount.countDown();
                    BaseValid.remove();
                }
            }
        };

        // 加开异步线程并行处理
        int threads = Math.min(paramMap.size(), context.getThreads() + 1);
        for (int i = 1; i < threads; i++) {
            taskExecutor.execute(execute);
        }

        // 主线程参与争抢执行任务并阻塞等待所有任务完成
        execute.run();
        taskTotalCount.await();
    }

    /**
     * @author lzq
     * @description 数据校验任务
     * @date 2023/4/23 15:30
     */
    @Data
    @AllArgsConstructor
    static class Task {

        private String dataId;

        private BaseApiParam apiParam;
    }

}
