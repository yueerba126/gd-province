package com.sydata.report.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.collect.param.QualityYearsPageParam;
import com.sydata.report.domain.ReportQualityYears;
import com.sydata.report.vo.ReportQualityVo;

/**
 * 数据上报-上报数据质量年报Service接口
 *
 * @author lzq
 * @date 2022-11-02
 */
public interface IReportQualityYearsService extends IService<ReportQualityYears> {

    /**
     * 上报数据质量年报-报表查询
     *
     * @param pageParam 数据质量统计年报表分页参数
     * @return 数据上报质量统计报表VO
     */
    Page<ReportQualityVo> report(QualityYearsPageParam pageParam);
}
