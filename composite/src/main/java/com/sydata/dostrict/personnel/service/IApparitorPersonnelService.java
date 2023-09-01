package com.sydata.dostrict.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.dostrict.personnel.domain.ApparitorPersonnel;
import com.sydata.dostrict.personnel.param.ApparitorPersonnelPageParam;
import com.sydata.dostrict.personnel.param.ApparitorPersonnelSaveParam;
import com.sydata.dostrict.personnel.vo.ApparitorPersonnelVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;
import java.util.List;

/**
 * 基础数据-企业人员 Service接口
 *
 * @author fuql
 * @date 2022-08-19
 */
public interface IApparitorPersonnelService extends IService<ApparitorPersonnel> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ApparitorPersonnelVo> pages(ApparitorPersonnelPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 企业人员信息VO
     */
    ApparitorPersonnelVo detail(String id);

    /**
     * 新增
     *
     * @param param 参数
     * @return 主键ID
     */
    String saveData(ApparitorPersonnelSaveParam param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateData(ApparitorPersonnelSaveParam param);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);

    /**
     * 删除
     *
     * @param ids 参数
     * @return 主键ID
     */
    Boolean removeData(List<String> ids);

}
