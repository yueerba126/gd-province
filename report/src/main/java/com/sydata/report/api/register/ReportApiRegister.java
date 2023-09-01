package com.sydata.report.api.register;

import com.sydata.collect.api.annotation.DataApi;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.report.api.invoker.ReportApiInvoker;
import com.sydata.report.api.service.IReportApiV2022;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzq
 * @describe 上报API注册中心
 * @date 2022/10/31 14:33
 */
@Component
public class ReportApiRegister {

    private Map<DataApiEnum, ReportApiInvoker> apiInvokerMap = new HashMap<>(64);

    @Resource
    private List<IReportApiV2022> reportApiV2022s;

    @PostConstruct
    public void init() {
        for (IReportApiV2022 reportApiV2022 : reportApiV2022s) {
            Method[] methods = ((Class) reportApiV2022.getClass().getGenericInterfaces()[0]).getMethods();
            for (Method method : methods) {
                DataApi dataApi = method.getAnnotation(DataApi.class);
                if (dataApi != null) {
                    ReportApiInvoker reportApiInvoker = new ReportApiInvoker(reportApiV2022, method);
                    apiInvokerMap.put(dataApi.value(), reportApiInvoker);
                }
            }
        }
    }

    /**
     * 查找调用器
     *
     * @param apiEnum api枚举
     * @return API调用器
     */
    public ReportApiInvoker findInvoker(DataApiEnum apiEnum) {
        return apiInvokerMap.get(apiEnum);
    }
}
