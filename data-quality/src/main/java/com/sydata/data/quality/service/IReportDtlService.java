package com.sydata.data.quality.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.data.quality.domain.ReportDtl;
import com.sydata.data.quality.vo.ReportDtlVo;

import java.util.List;

/**
 * 数据质量-报告明细Service接口
 *
 * @author system
 * @date 2023-04-18
 */
public interface IReportDtlService extends IService<ReportDtl> {


    /**
     * 根据报告ID查询报告明细VO列表
     *
     * @param reportId 数据质量报告ID
     * @return 报告明细VO列表
     */
    List<ReportDtlVo> listByReportId(String reportId);

    /**
     * 根据报告ID列表查询报告明细列表
     *
     * @param reportIds 数据质量报告ID列表
     * @return 报告明细列表
     */
    List<ReportDtl> listByReportIds(List<String> reportIds);
}