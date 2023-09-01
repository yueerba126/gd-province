package com.sydata.dostrict.reserve.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.dostrict.reserve.domain.ApparitorRotationPlanDtl;
import com.sydata.dostrict.reserve.param.ApparitorRotationPlanDtlSaveParam;
import com.sydata.dostrict.reserve.param.ApparitorRotationPlanDtlPageParam;
import com.sydata.dostrict.reserve.vo.ApparitorRotationPlanDtlVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;
import java.util.List;

/**
 * 粮食储备-轮换计划明细表Service接口
 *
 * @author lzq
 * @date 2023-04-26
 */
public interface IApparitorRotationPlanDtlService extends IService<ApparitorRotationPlanDtl> {


    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ApparitorRotationPlanDtlVo> pages(ApparitorRotationPlanDtlPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    ApparitorRotationPlanDtlVo detail(String id);

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

    /**
     * 新增
     *
     * @param param 参数
     * @return 主键ID
     */
    String saveData(ApparitorRotationPlanDtlSaveParam param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateData(ApparitorRotationPlanDtlSaveParam param);

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
