package com.sydata.organize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.organize.domain.AppApiStockHouse;
import com.sydata.organize.param.AppApiStockHousePageParam;
import com.sydata.organize.param.AppApiStockHouseRemoveParam;
import com.sydata.organize.vo.AppApiStockHouseVo;

/**
 * @author lzq
 * @description app对接应用关联库区Service接口
 * @date 2023/5/30 11:32
 */
public interface IAppApiStockHouseService extends IService<AppApiStockHouse> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<AppApiStockHouseVo> pages(AppApiStockHousePageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    AppApiStockHouseVo detail(String id);

    /**
     * 删除
     *
     * @param removeParam 应用库区删除参数
     * @return 删除状态
     */
    Boolean remove(AppApiStockHouseRemoveParam removeParam);

    /**
     * 根据应用和库区查询
     *
     * @param appid        应用ID
     * @param stockHouseId 库区ID
     */
    AppApiStockHouse getByAppStockHouse(String appid, String stockHouseId);
}
