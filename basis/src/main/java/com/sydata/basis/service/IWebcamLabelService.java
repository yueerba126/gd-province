package com.sydata.basis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.basis.domain.WebcamLabel;
import com.sydata.basis.param.WebcamLabelPageParam;
import com.sydata.basis.vo.WebcamLabelVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;
import java.util.List;

/**
 * 库区图视频监控设备点位标注Service接口
 *
 * @author lzq
 * @date 2022-10-11
 */
public interface IWebcamLabelService extends IService<WebcamLabel> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<WebcamLabelVo> pages(WebcamLabelPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    WebcamLabelVo detail(String id);

    /**
     * 根据库区获取设备点位标注列表
     *
     * @param stockHouseId 库区ID
     * @return 设备点位标注列表
     */
    List<WebcamLabel> listByStockHouseId(String stockHouseId);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);

}