package com.sydata.record.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.record.domain.Fumigation;
import com.sydata.record.param.FumigationApprovedParam;
import com.sydata.record.param.FumigationPageParam;
import com.sydata.record.vo.FumigationDetailsVo;
import com.sydata.record.vo.FumigationPageVo;

/**
 * 备案管理-熏蒸Service接口
 *
 * @author system
 * @date 2022-12-10
 */
public interface IFumigationService extends IService<Fumigation> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<FumigationPageVo> pages(FumigationPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    FumigationDetailsVo detail(String id);

    /**
     * 审核
     *
     * @param param 熏蒸审核参数
     * @return 操作状态
     */
    boolean approved(FumigationApprovedParam param);
}