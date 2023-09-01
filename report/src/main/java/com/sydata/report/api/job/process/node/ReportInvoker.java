
package com.sydata.report.api.job.process.node;

import cn.hutool.core.map.MapUtil;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.register.DataRegister;
import com.sydata.collect.api.service.IData;
import com.sydata.report.domain.DataHandle;
import com.sydata.report.api.execute.ReportApiExecute;
import com.sydata.report.api.job.ReportProcessContext;
import com.sydata.report.api.job.process.enums.ReportProcessEnum;
import com.sydata.report.api.job.process.IReportProcess;
import com.sydata.report.vo.ReportResultVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.sydata.report.api.job.process.enums.ReportProcessEnum.INVOKER;

/**
 * @author lzq
 * @description 数据上报数据处理-执行上报
 * @date 2022/11/2 14:50
 */
@Component
public class ReportInvoker implements IReportProcess {

    @Resource
    private ReportApiExecute reportApiExecute;

    @Resource
    private DataRegister dataRegister;

    @Override
    public ReportProcessEnum node() {
        return INVOKER;
    }

    /**
     * 1.apiJson参数反序列化成收集实例,并设置操作标识
     * 2.执行上报
     */
    @Override
    public void process(ReportProcessContext context) {
        List<DataHandle> dataHandles = context.getDataHandles();
        if (CollectionUtils.isEmpty(dataHandles)) {
            return;
        }

        DataApiEnum dataApiEnum = context.getDataApiEnum();

        // 将JSON参数反序列化成收集参数实例并设置数据上报操作标识
        IData data = dataRegister.getByDataApi(dataApiEnum);
        Map<String, BaseApiParam> dataIdParamMap = MapUtil.newHashMap(dataHandles.size());
        dataHandles.forEach(dataHandle -> {
            BaseApiParam baseApiParam = (BaseApiParam) data.toParam(dataHandle.getParam());
            dataIdParamMap.put(dataHandle.getDataId(), baseApiParam.setCzbz(dataHandle.getCzbz()));
        });

        // 执行上报
        Map<BaseApiParam, ReportResultVo> resultMap = reportApiExecute.execute(dataApiEnum, dataIdParamMap.values());
        context.setDataIdParamMap(dataIdParamMap).setResultMap(resultMap);
    }
}
