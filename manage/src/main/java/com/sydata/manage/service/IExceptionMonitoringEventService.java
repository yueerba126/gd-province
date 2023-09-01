package com.sydata.manage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.manage.domain.ExceptionMonitoringEvent;
import com.sydata.manage.param.ExceptionMonitoringEventPageParam;
import com.sydata.manage.vo.ExceptionMonitoringEventVo;

import java.util.Collection;

/**
 * 粮库管理-库区视频监控异常事件告警Service接口
 *
 * @author lzq
 * @date 2022-07-25
 */
public interface IExceptionMonitoringEventService extends IService<ExceptionMonitoringEvent> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ExceptionMonitoringEventVo> pages(ExceptionMonitoringEventPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    ExceptionMonitoringEventVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}