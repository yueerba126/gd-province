package com.sydata.report.api.execute;

import cn.hutool.core.map.MapUtil;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.register.DataRegister;
import com.sydata.collect.api.service.IData;
import com.sydata.report.api.config.ReportConfig;
import com.sydata.report.api.invoker.ReportApiInvoker;
import com.sydata.report.api.param.BaseReportParam;
import com.sydata.report.api.register.ReportApiRegister;
import com.sydata.report.enums.ReportCodeEnum;
import com.sydata.report.vo.ReportResultVo;
import lombok.SneakyThrows;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;

import static java.util.function.Function.identity;

/**
 * @author lzq
 * @description 上报API执行器
 * @date 2022/11/1 16:10
 */
@Component
public class ReportApiExecute {

    @Resource
    private DataRegister dataRegister;

    @Resource
    private ReportApiRegister reportApiRegister;

    @Resource
    private Executor taskExecutor;

    private final Semaphore invokerSemaphore;

    public ReportApiExecute(ReportConfig reportConfig) {
        this.invokerSemaphore = new Semaphore(reportConfig.getMaxRows());
    }

    /**
     * 执行上报
     *
     * @param dataApiEnum 数据API枚举
     * @param params      请求参数列表
     * @return 请求参数和上报结果的Map映射
     */
    @SneakyThrows(Throwable.class)
    public Map<BaseApiParam, ReportResultVo> execute(DataApiEnum dataApiEnum, Collection<BaseApiParam> params) {
        // 将收集参数转换为上报参数
        IData<BaseApiParam, ?, BaseReportParam> data = dataRegister.getByDataApi(dataApiEnum);
        Map<BaseReportParam, BaseApiParam> reportMap = StreamEx.of(params).toMap(data::toReport, identity());

        // 调用接口上报（批量上报）
        ReportApiInvoker apiInvoker = reportApiRegister.findInvoker(dataApiEnum);
        Assert.notNull(apiInvoker, "未定义上报API调用接口");
        ReportResultVo resultVo = apiInvoker.invoker(new ArrayList<>(reportMap.keySet()), invokerSemaphore);

        // 如果成功统一设置响应结果,否则循环参数一个个调用接口上报(区分到底哪些成功/哪些失败)
        Map<BaseApiParam, ReportResultVo> resultMap = MapUtil.newHashMap(params.size());
        if (ReportCodeEnum.SUCCESS.getCode().equals(resultVo.getCode())) {
            reportMap.values().forEach(param -> resultMap.put(param, resultVo));
        } else {
            // 多线程上报
            CountDownLatch countDownLatch = new CountDownLatch(reportMap.size());
            reportMap.forEach((report, param) -> taskExecutor.execute(() -> {
                try {
                    List<BaseReportParam> singleton = Collections.singletonList(report);
                    ReportResultVo vo = apiInvoker.invoker(singleton, invokerSemaphore);
                    resultMap.put(param, vo);
                } finally {
                    countDownLatch.countDown();
                }
            }));
            countDownLatch.await();
        }

        // 返回上报结果
        return resultMap;
    }
}
