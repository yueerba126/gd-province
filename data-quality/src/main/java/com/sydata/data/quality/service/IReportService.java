package com.sydata.data.quality.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.data.quality.domain.Report;
import com.sydata.data.quality.enums.ReportTargetTypeEnum;
import com.sydata.data.quality.param.ReportPageParam;
import com.sydata.data.quality.vo.ReportVo;

import java.time.LocalDate;
import java.util.List;

/**
 * 数据质量-报告Service接口
 *
 * @author system
 * @date 2023-04-18
 */
public interface IReportService extends IService<Report> {


    /**
     * 查询报告列表
     *
     * @param pageParam 报告分页参数
     * @return 报告VO列表
     */
    Page<ReportVo> pages(ReportPageParam pageParam);

    /**
     * 批量获取库区报告
     *
     * @param targetType 目标类型
     * @param targetIds  目标ID列表
     * @param reportDate 报告日期
     * @return 库区报告列表
     */
    List<Report> listByTargetIds(ReportTargetTypeEnum targetType, List<String> targetIds, LocalDate reportDate);
}