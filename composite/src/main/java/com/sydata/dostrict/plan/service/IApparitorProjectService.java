package com.sydata.dostrict.plan.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.dostrict.plan.domain.ApparitorProject;
import com.sydata.dostrict.plan.param.ApparitorProjectPageParam;
import com.sydata.dostrict.plan.param.ApparitorProjectSaveParam;
import com.sydata.dostrict.plan.vo.ApparitorProjectVo;

import java.util.List;

/**
 * @program: gd-province-platform
 * @description: 规划建设-项目管理 Service接口
 * @author: lzq
 * @create: 2023-04-24 18:47
 */
public interface IApparitorProjectService extends IService<ApparitorProject> {
    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ApparitorProjectVo> pages(ApparitorProjectPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return VO
     */
    ApparitorProjectVo detail(String id);

    /**
     * 新增
     *
     * @param param 参数
     * @return 主键ID
     */
    String saveData(ApparitorProjectSaveParam param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateData(ApparitorProjectSaveParam param);

    /**
     * 删除
     *
     * @param ids 参数
     * @return 主键ID
     */
    Boolean removeData(List<String> ids);
}
