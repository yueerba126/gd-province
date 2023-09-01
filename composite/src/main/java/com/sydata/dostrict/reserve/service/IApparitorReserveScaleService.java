package com.sydata.dostrict.reserve.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.dostrict.reserve.domain.ApparitorReserveScale;
import com.sydata.dostrict.reserve.param.ApparitorReserveScaleSaveParam;
import com.sydata.dostrict.reserve.param.ApparitorReserveScalePageParam;
import com.sydata.dostrict.reserve.vo.ApparitorReserveScaleVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;
import java.util.List;

/**
 * 粮食储备-储备规模Service接口
 *
 * @author lzq
 * @date 2023-04-26
 */
public interface IApparitorReserveScaleService extends IService<ApparitorReserveScale> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ApparitorReserveScaleVo> pages(ApparitorReserveScalePageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    ApparitorReserveScaleVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);

    /**
     * 新增
     *
     * @param param 参数
     * @return 主键ID
     */
    String saveData(ApparitorReserveScaleSaveParam param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateData(ApparitorReserveScaleSaveParam param);

    /**
     * 删除
     *
     * @param ids 参数
     * @return 主键ID
     */
    Boolean removeData(List<String> ids);
}