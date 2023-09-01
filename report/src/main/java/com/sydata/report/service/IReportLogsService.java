package com.sydata.report.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.report.domain.ReportLogs;
import com.sydata.report.param.ReportLogsPageParam;
import com.sydata.report.vo.ReportLogsVo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 数据上报-上报日志Service接口
 *
 * @author lzq
 * @date 2022-11-02
 */
public interface IReportLogsService extends IService<ReportLogs> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ReportLogsVo> pages(ReportLogsPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    ReportLogsVo detail(String id);

    /**
     * 重报投递
     *
     * @param ids 主键列表
     * @return 投递结果
     */
    Boolean delivery(List<String> ids);


    /**
     * 根据上报时间的结束区间查询企业列表
     *
     * @param endTime 请求时间的结束区间
     * @return 企业列表
     */
    List<String> enterpriseIdsByEndTime(LocalDateTime endTime);

    /**
     * 根据上报时间的结束区间和企业列表查询数据
     *
     * @param endTime       请求时间的结束区间
     * @param enterpriseIds 企业列表
     * @return 上报日志列表
     */
    List<ReportLogs> listByEndTime(LocalDateTime endTime, List<String> enterpriseIds);

    /**
     * 根据上报时间的结束区间和企业列表设置数据为已处理
     *
     * @param endTime       请求时间的结束区间
     * @param enterpriseIds 企业列表
     * @return 处理结果
     */
    boolean handleByEndTime(LocalDateTime endTime, List<String> enterpriseIds);
}
