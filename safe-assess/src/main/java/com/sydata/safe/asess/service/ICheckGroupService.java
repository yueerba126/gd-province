package com.sydata.safe.asess.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.safe.asess.domain.CheckGroup;
import com.sydata.safe.asess.param.CheckGroupPageParam;
import com.sydata.safe.asess.param.CheckGroupSaveParam;
import com.sydata.safe.asess.param.CheckGroupUpdateParam;
import com.sydata.safe.asess.vo.CheckGroupVo;

import java.util.Collection;

/**
 * 粮安-实地抽查小组管理Service接口
 *
 * @author system
 * @date 2023-02-10
 */
public interface ICheckGroupService extends IService<CheckGroup> {


    /**
     * 分页列表
     *
     * @param pageParam 分页对象
     * @return 分页列表
     */
    Page<CheckGroupVo> pages(CheckGroupPageParam pageParam);


    /**
     * 详情
     *
     * @param id 小组ID
     * @return 抽查小组VO
     */
    CheckGroupVo detail(String id);

    /**
     * 新增
     *
     * @param checkGroupSaveParam 抽查小组新增参数
     * @return 新增结果
     */
    boolean save(CheckGroupSaveParam checkGroupSaveParam);

    /**
     * 修改
     *
     * @param checkGroupUpdateParam 抽查小组修改参数
     * @return 修改结果
     */
    Boolean update(CheckGroupUpdateParam checkGroupUpdateParam);

    /**
     * 检查人员关联小组个数
     *
     * @param checkUserId 检查人员ID
     * @return 关联小组个数
     */
    Long checkUserRelationCount(String checkUserId);

    /**
     * 操作权限
     *
     * @param id 考核模板ID
     * @return 权限标识
     */
    Boolean operationAuth(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}