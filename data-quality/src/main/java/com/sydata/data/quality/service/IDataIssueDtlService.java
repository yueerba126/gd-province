package com.sydata.data.quality.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.data.quality.domain.DataIssueDtl;

import java.util.List;

/**
 * 数据质量-数据问题明细Service接口
 *
 * @author system
 * @date 2023-04-18
 */
public interface IDataIssueDtlService extends IService<DataIssueDtl> {

    /**
     * 查询数据问题列表
     *
     * @param issueDataId 数据问题ID
     * @return 数据问题明细列表
     */
    List<DataIssueDtl> listByIssueDataId(String issueDataId);

    /**
     * 根据数据问题ID列表查询报告明细列表
     *
     * @param issueDataIds 数据问题ID列表
     * @return 报告明细列表
     */
    List<DataIssueDtl> listByIssueDataIds(List<String> issueDataIds);
}