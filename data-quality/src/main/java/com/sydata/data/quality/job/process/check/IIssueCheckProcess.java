package com.sydata.data.quality.job.process.check;

import com.sydata.collect.quality.DataQualityProcessContext;
import com.sydata.data.quality.job.process.check.enums.IssueCheckEnum;

/**
 * @author lzq
 * @description 数据问题检查顶级接口
 * @date 2023/4/23 15:04
 */
public interface IIssueCheckProcess {

    /**
     * 数据质量处理枚举以标注节点
     *
     * @return 数据质量处理枚举
     */
    IssueCheckEnum node();

    /**
     * 处理数据
     *
     * @param context 数据质量处理上下文
     */
    void process(DataQualityProcessContext context);
}
