package com.sydata.collect.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.collect.domain.RequestQualityYears;
import com.sydata.collect.param.QualityYearsPageParam;
import com.sydata.collect.vo.CollectQualityVo;

import java.util.List;

/**
 * 数据收集-请求数据质量年报Service接口
 *
 * @author lzq
 * @date 2022-10-27
 */
public interface IRequestQualityYearsService extends IService<RequestQualityYears> {


    /**
     * 请求数据质量年报-报表查询
     *
     * @param pageParam 数据质量统计年报表分页参数
     * @return 数据收集质量统计报表VO
     */
    Page<CollectQualityVo> report(QualityYearsPageParam pageParam);

    /**
     * 根据库区ID和接口编号获取请求数据质量
     *
     * @param stockHouseIds 库区ID列表
     * @param apiCodes      接口编号列表
     * @return 请求数据质量年报列表
     */
    List<RequestQualityYears> listByStockHouseIds(List<String> stockHouseIds, List<String> apiCodes);
}