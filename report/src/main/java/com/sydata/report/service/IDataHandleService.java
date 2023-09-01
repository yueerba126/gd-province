package com.sydata.report.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.collect.api.event.DataCollectEvent;
import com.sydata.report.domain.DataHandle;
import com.sydata.report.param.DataHandlePageParam;
import com.sydata.report.vo.DataHandleVo;
import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 数据收集-数据处理Service接口
 *
 * @author lzq
 * @date 2022-10-21
 */
public interface IDataHandleService extends IService<DataHandle> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<DataHandleVo> pages(DataHandlePageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    DataHandleVo detail(String id);

    /**
     * 监听数据收集事件
     *
     * @param dataCollectEvent 数据收集事件
     */
    @EventListener(DataCollectEvent.class)
    void dataCollect(DataCollectEvent dataCollectEvent);

    /**
     * 根据指定的时间查询未处理ID列表
     *
     * @param apiCode 接口编号
     * @param endTime 请求时间的结束区间
     * @return 未处理ID列表
     */
    List<String> notHandleIdsByEndTime(String apiCode, LocalDateTime endTime);

    /**
     * 根据指定的时间和数据ID列表设置数据为已处理
     *
     * @param apiCode 接口编号
     * @param endTime 请求时间的结束区间
     * @param dataIds 数据ID列表
     * @return 影响行数
     */
    int handleByEndTimeAndDataId(String apiCode, LocalDateTime endTime, List<String> dataIds);
}
