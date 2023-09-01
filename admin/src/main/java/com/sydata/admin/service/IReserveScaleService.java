package com.sydata.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.admin.domain.ReserveScale;
import com.sydata.admin.param.ReserveScalePageParam;
import com.sydata.admin.vo.ReserveScaleVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;

/**
 * 行政管理-储备规模Service接口
 *
 * @author lzq
 * @date 2022-07-25
 */
public interface IReserveScaleService extends IService<ReserveScale> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ReserveScaleVo> pages(ReserveScalePageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    ReserveScaleVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}