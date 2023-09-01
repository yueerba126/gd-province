package com.sydata.report.api.invoker;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.sydata.report.api.param.BaseReportParam;
import com.sydata.report.api.service.IReportApiV2022;
import com.sydata.report.enums.ReportCodeEnum;
import com.sydata.report.vo.ReportResultVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @author lzq
 * @describe API上报调用器
 * @date 2022/10/31 14:33
 */
@Slf4j
@AllArgsConstructor
public class ReportApiInvoker {

    @ApiModelProperty(value = "上报接口")
    private IReportApiV2022 reportApiV2022;

    @ApiModelProperty(value = "上报方法")
    private Method apiMethod;


    /**
     * 调用上报api
     *
     * @param reports          上报参数
     * @param invokerSemaphore 调用信号量
     * @return 上报统一返回参数
     */
    @SneakyThrows(Throwable.class)
    public ReportResultVo invoker(List<BaseReportParam> reports, Semaphore invokerSemaphore) {
        ReportResultVo resultVo = null;
        LocalDateTime beginTime = null;
        try {
            invokerSemaphore.acquire();

            beginTime = LocalDateTime.now();
            resultVo = (ReportResultVo) apiMethod.invoke(reportApiV2022, reports);
        } catch (Throwable e) {
            resultVo = new ReportResultVo().setCode(ReportCodeEnum.FAIL.getCode()).setInvokerMsg(e.getMessage());
            log.error("调用国家平台上报接口异常：", e);
        } finally {
            invokerSemaphore.release();

            LocalDateTime endTime = LocalDateTime.now();
            long timeConsuming = Duration.between(beginTime, endTime).toMillis();

            resultVo.setBeginTime(beginTime)
                    .setEndTime(endTime)
                    .setTimeConsuming(timeConsuming)
                    .setInvokerBatchId(IdWorker.getIdStr());
        }
        return resultVo;
    }
}
