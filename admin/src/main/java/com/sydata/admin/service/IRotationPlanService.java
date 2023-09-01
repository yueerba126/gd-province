package com.sydata.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.admin.domain.RotationPlan;
import com.sydata.admin.param.RotationPlanPageParam;
import com.sydata.admin.vo.RotationPlanVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;

/**
 * <p>
 * 轮换计划主表 服务类
 * </p>
 *
 * @author lzq
 * @since 2022-05-06
 */
public interface IRotationPlanService extends IService<RotationPlan> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<RotationPlanVo> pages(RotationPlanPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    RotationPlanVo detail(String id);

    /**
     * 生成轮换计划单号（由计划下达单位统一社会信用代码+计划年度（yyyy）+3 位顺序号组成）
     *
     * @param jhxddw 计划下达单位代码
     * @param jhnd   计划年度
     * @return 轮换计划单号
     */
    String generate(String jhxddw, String jhnd);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
