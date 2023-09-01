package com.sydata.report.api.job.process;

import com.sydata.report.api.job.ReportProcessContext;
import com.sydata.report.api.job.process.enums.ReportProcessEnum;

/**
 * @author lzq
 * @description 数据上报数据处理顶级接口
 * @date 2022/11/2 14:40
 */
public interface IReportProcess {

    /**
     * 以上报处理枚举表明身份
     *
     * @return 上报处理枚举
     */
    ReportProcessEnum node();

    /**
     * 处理数据
     *
     * @param context 上报数据处理上下文
     */
    void process(ReportProcessContext context);
}
