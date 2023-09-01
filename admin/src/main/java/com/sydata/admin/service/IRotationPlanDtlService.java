package com.sydata.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.admin.domain.RotationPlanDtl;
import com.sydata.admin.param.RotationPlanDtlPageParam;
import com.sydata.admin.vo.RotationPlanDtlVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;

/**
 * <p>
 * 轮换计划明细表 服务类
 * </p>
 *
 * @author lzq
 * @since 2022-05-06
 */
public interface IRotationPlanDtlService extends IService<RotationPlanDtl> {


    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<RotationPlanDtlVo> pages(RotationPlanDtlPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    RotationPlanDtlVo detail(String id);

    /**
     * 生成轮换计划明细单号（由轮换计划单号+库区代码+四位顺序码组成）
     *
     * @param lhjhdh 轮换计划单号
     * @param hwdm   货位代码
     * @return 轮换计划明细单号
     */
    String generate(String lhjhdh, String hwdm);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
