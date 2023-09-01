package com.sydata.dostrict.reserve.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.dostrict.reserve.domain.ApparitorRotationPlan;
import com.sydata.dostrict.reserve.param.ApparitorRotationPlanMasterSlaveParam;
import com.sydata.dostrict.reserve.param.ApparitorRotationPlanPageParam;
import com.sydata.dostrict.reserve.param.ApparitorRotationPlanSaveParam;
import com.sydata.dostrict.reserve.vo.ApparitorRotationPlanMasterSlaveVo;
import com.sydata.dostrict.reserve.vo.ApparitorRotationPlanVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;
import java.util.List;

/**
 * 粮食储备-轮换计划主表Service接口
 *
 * @author lzq
 * @date 2023-04-26
 */
public interface IApparitorRotationPlanService extends IService<ApparitorRotationPlan> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ApparitorRotationPlanVo> pages(ApparitorRotationPlanPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    ApparitorRotationPlanVo detail(String id);

    /**
     * 详情 - 主从表
     *
     * @param id 主键ID
     * @return 详情
     */
    ApparitorRotationPlanMasterSlaveVo detailMasterSlave(String id);

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

    /**
     * 新增
     *
     * @param param 参数
     * @return 主键ID
     */
    String saveData(ApparitorRotationPlanSaveParam param);

    /**
     * 操作主从表数据
     *
     * @param param 参数
     * @return 主键ID
     */
    String saveOrUpdateMasterSlaveData(ApparitorRotationPlanMasterSlaveParam param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateData(ApparitorRotationPlanSaveParam param);

    /**
     * 删除
     *
     * @param ids 参数
     * @return 主键ID
     */
    Boolean removeData(List<String> ids);

    /**
     * 删除
     *
     * @param ids 参数
     * @return 主键ID
     */
    Boolean removeDataNotInList(List<String> ids);
}
