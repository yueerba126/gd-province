package com.sydata.manage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.manage.domain.GranaryVideoImage;
import com.sydata.manage.param.GranaryVideoImagePageParam;
import com.sydata.manage.vo.GranaryVideoImageVo;

import java.util.Collection;

/**
 * 粮库管理-仓内视频图像Service接口
 *
 * @author lzq
 * @date 2022-07-25
 */
public interface IGranaryVideoImageService extends IService<GranaryVideoImage> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<GranaryVideoImageVo> pages(GranaryVideoImagePageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    GranaryVideoImageVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
