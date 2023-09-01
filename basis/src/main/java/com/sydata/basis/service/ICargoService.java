package com.sydata.basis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.basis.domain.Cargo;
import com.sydata.basis.param.CargoPageParam;
import com.sydata.basis.vo.CargoVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;

/**
 * 基础信息-货位信息Service接口
 *
 * @author lzq
 * @date 2022-07-08
 */
public interface ICargoService extends IService<Cargo> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<CargoVo> pages(CargoPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    CargoVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}