package com.sydata.manage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.manage.domain.SteamTaskInformation;
import com.sydata.manage.param.SteamTaskInformationPageParam;
import com.sydata.manage.vo.SteamTaskInformationVo;

import java.util.Collection;

/**
 * <p>
 * 蒸熏作业信息 服务类
 * </p>
 *
 * @author lzq
 * @since 2022-08-18
 */
public interface ISteamTaskInformationService extends IService<SteamTaskInformation> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<SteamTaskInformationVo> pages(SteamTaskInformationPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    SteamTaskInformationVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
