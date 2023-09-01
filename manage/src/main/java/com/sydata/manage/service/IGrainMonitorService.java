package com.sydata.manage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.manage.domain.GrainMonitor;
import com.sydata.manage.param.GrainMonitorPageParam;
import com.sydata.manage.vo.GrainMonitorVo;

import java.util.Collection;

/**
 * 温湿度监测信息接口
 *
 * @author lzq
 * @date 2022/8/19 10:52
 */
public interface IGrainMonitorService extends IService<GrainMonitor> {

    /**
     * 分页查询
     *
     * @param pageParam 分页参数
     * @return Page<GrainMonitorVO>
     */
    Page<GrainMonitorVo> pages(GrainMonitorPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    GrainMonitorVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
