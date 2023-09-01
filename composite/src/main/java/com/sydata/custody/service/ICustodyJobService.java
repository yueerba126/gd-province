package com.sydata.custody.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.custody.dto.GrainCustodyJobsQueryDto;
import com.sydata.custody.vo.GrainSteamTaskInformationVo;
import com.sydata.custody.vo.GrainVentilationJobVo;

/**
 * 保管作业统一业务服务
 *
 * @author zhangcy
 * @date 2023/4/23 14:46
 */
public interface ICustodyJobService {

    /**
     * 查询粮库通风作业分页
     *
     * @param queryDto 查询参数
     * @return 分页结果
     */
    Page<GrainVentilationJobVo> grainVentilationPage(GrainCustodyJobsQueryDto queryDto);

    /**
     * 查询粮库通风作业详情
     *
     * @param id 通风作业ID
     * @return VO
     */
    GrainVentilationJobVo ventilationJobVoDetail(String id);

    /**
     * 查询粮库熏蒸作业分页
     *
     * @param queryDto 查询参数
     * @return 分页结果
     */
    Page<GrainSteamTaskInformationVo> grainSteamTaskInformationPage(GrainCustodyJobsQueryDto queryDto);

    /**
     * 查询粮库熏蒸作业详情
     *
     * @param id 熏蒸作业ID
     * @return VO
     */
    GrainSteamTaskInformationVo grainSteamTaskInformationDetail(String id);
}
