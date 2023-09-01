package com.sydata.organize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.organize.domain.Region;
import com.sydata.organize.param.RegionPageParam;
import com.sydata.organize.param.RegionRemoveParam;
import com.sydata.organize.vo.RegionTreeVo;

import java.util.Collection;
import java.util.List;

/**
 * 组织架构-行政区域Service接口
 *
 * @author lzq
 * @date 2022-06-28
 */
public interface IRegionService extends IService<Region> {

    /**
     * 分页列表
     *
     * @param pageParam 行政区域分页参数
     * @return 行政区域列表
     */
    Page<Region> pages(RegionPageParam pageParam);

    /**
     * 树列表
     *
     * @return 树列表
     */
    List<RegionTreeVo> tree();

    /**
     * 根据父ID获取行政区域列表
     *
     * @param parentId 父行政区域代码
     * @return 行政区域列表
     */
    List<Region> listByParentId(String parentId);

    /**
     * 根据名称获取行政区域
     *
     * @param name 行政区域名称
     * @return 行政区域列表
     */
    List<Region> listByName(String name);

    /**
     * 删除
     *
     * @param removeParam 行政区域删除参数
     * @return 删除结果
     */
    Boolean remove(RegionRemoveParam removeParam);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);

    /**
     * 根据当前行政区划id获取下级行政区域列表
     *
     * @param id id
     * @return 行政区域列表
     */
    List<Region> tabulation(String id);

    /**
     * 根据父ID获取当前行政区域和下级行政区划代码列表
     *
     * @param parentId parentId
     * @return 行政区域列表
     */
    List<Region> sonsAll(String parentId);
}