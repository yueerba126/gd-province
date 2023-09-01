package com.sydata.report.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.collect.param.QualityDayPageParam;
import com.sydata.report.domain.ReportQualityDay;
import com.sydata.report.vo.ReportQualityVo;

import java.time.LocalDate;
import java.util.List;

/**
 * 数据上报-上报数据质量日报Service接口
 *
 * @author lzq
 * @date 2022-11-02
 */
public interface IReportQualityDayService extends IService<ReportQualityDay> {

    /**
     * 上报数据质量日报-报表查询
     *
     * @param pageParam 数据质量统计日报表分页参数
     * @return 数据上报质量统计报表VO
     */
    Page<ReportQualityVo> report(QualityDayPageParam pageParam);

    /**
     * 根据指定日期查询企业列表
     *
     * @param days 日期
     * @return 企业列表
     */
    List<String> enterpriseIdsByDays(LocalDate days);

    /**
     * 根据指定日期和企业查询数据
     *
     * @param days          日期
     * @param enterpriseIds 企业列表
     * @return 上报数据质量日报列表
     */
    List<ReportQualityDay> listByDays(LocalDate days, List<String> enterpriseIds);

    /**
     * 根据指定日期和企业设置数据为已处理
     *
     * @param days          日期
     * @param enterpriseIds 企业列表
     * @return 处理结果
     */
    boolean handleByDays(LocalDate days, List<String> enterpriseIds);
}
