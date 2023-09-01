package com.sydata.basis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.basis.domain.CargoLabel;
import com.sydata.basis.param.CargoLabelPageParam;
import com.sydata.basis.vo.CargoLabelVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;

/**
 * 库区图仓房点位标注Service接口
 *
 * @author lzq
 * @date 2022-10-11
 */
public interface ICargoLabelService extends IService<CargoLabel> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<CargoLabelVo> pages(CargoLabelPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    CargoLabelVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}