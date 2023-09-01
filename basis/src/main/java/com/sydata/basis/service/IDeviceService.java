package com.sydata.basis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.basis.domain.Device;
import com.sydata.basis.param.DevicePageParam;
import com.sydata.basis.vo.DeviceVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;

/**
 * 基础信息-设备信息Service接口
 *
 * @author lzq
 * @date 2022-07-08
 */
public interface IDeviceService extends IService<Device> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<DeviceVo> pages(DevicePageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    DeviceVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}