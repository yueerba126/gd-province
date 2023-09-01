package com.sydata.collect.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.collect.domain.RequestLog;
import com.sydata.collect.param.RequestLogPageParam;
import com.sydata.collect.vo.RequestLogVo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 数据收集-请求日志Service接口
 *
 * @author lzq
 * @date 2022-10-21
 */
public interface IRequestLogService extends IService<RequestLog> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<RequestLogVo> pages(RequestLogPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    RequestLogVo detail(String id);

    /**
     * 插入缓冲区
     *
     * @param requestLog 请求日志
     */
    void saveByBuffer(RequestLog requestLog);

    /**
     * 根据请求时间的结束区间查询企业列表
     *
     * @param endTime 请求时间的结束区间
     * @return 企业列表
     */
    List<String> enterpriseIdsByEndTime(LocalDateTime endTime);

    /**
     * 根据请求时间的结束区间和企业列表查询数据
     *
     * @param endTime       请求时间的结束区间
     * @param enterpriseIds 企业列表
     * @return 请求日志列表
     */
    List<RequestLog> listByEndTime(LocalDateTime endTime, List<String> enterpriseIds);

    /**
     * 根据请求时间的结束区间和企业列表设置数据为已处理
     *
     * @param endTime       请求时间的结束区间
     * @param enterpriseIds 企业列表
     * @return 处理结果
     */
    boolean handleByEndTime(LocalDateTime endTime, List<String> enterpriseIds);
}