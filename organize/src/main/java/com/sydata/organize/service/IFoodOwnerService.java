package com.sydata.organize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.organize.domain.FoodOwner;
import com.sydata.organize.param.FoodOwnerPageParam;
import com.sydata.organize.vo.FoodOwnerVo;

import java.util.List;
import java.util.Set;

/**
 * 组织架构-粮权关系维护Service接口
 *
 * @author lzq
 * @date 2022-11-14
 */
public interface IFoodOwnerService extends IService<FoodOwner> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<FoodOwnerVo> pages(FoodOwnerPageParam pageParam);

    /**
     * 根据粮权单位ID查询库区ID列表
     *
     * @param companyId 粮权单位ID
     * @return 库区ID列表
     */
    Set<String> stockHouseIdsByCompanyId(String companyId);

    /**
     * 根据区域ID查询库区ID列表
     *
     * @param regionId 区域ID
     * @return 库区ID列表
     */
    Set<String> stockHouseIdsByRegionId(String regionId);

    /**
     * 根据区域ID查询区域和库区粮权关系列表
     *
     * @param regionId 区域ID
     * @return 区域和库区粮权关系列表
     */
    List<FoodOwner> regionStockHousesByRegionId(String regionId);

    /**
     * 插入缓冲区
     *
     * @param regionId     区域ID
     * @param companyId    粮权单位ID
     * @param enterpriseId 企业ID
     * @param stockHouseId 库区ID
     */
    void saveByBuffer(String regionId, String companyId, String enterpriseId, String stockHouseId);
}