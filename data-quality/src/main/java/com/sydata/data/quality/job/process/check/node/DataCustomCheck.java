package com.sydata.data.quality.job.process.check.node;

import com.sydata.collect.quality.DataQualityProcessContext;
import com.sydata.data.quality.job.process.check.IIssueCheckProcess;
import com.sydata.data.quality.job.process.check.enums.IssueCheckEnum;
import org.springframework.stereotype.Component;

/**
 * @author lzq
 * @description 数据自定义校验处理器
 * @date 2023/4/23 16:20
 */
@Component
public class DataCustomCheck implements IIssueCheckProcess {

    @Override
    public IssueCheckEnum node() {
        return IssueCheckEnum.DATA_CUSTOM_CHECK;
    }

    @Override
    public void process(DataQualityProcessContext context) {
        context.getDataApi().dataQualityCustomCheck(context);
    }
}
