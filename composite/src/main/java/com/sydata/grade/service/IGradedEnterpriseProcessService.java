/**
 * @filename:GradedEnterpriseProcessService 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.grade.domain.GradedEnterpriseProcess;
import com.sydata.grade.param.GradedEnterpriseProcessPageParam;
import com.sydata.grade.param.GradedEnterpriseProcessSaveParam;
import com.sydata.grade.vo.GradedEnterpriseProcessVo;

import java.util.Collection;
import java.util.List;

/**
 * @Description:TODO(等级粮库评定管理-企业申报审核详情服务层)
 * @version: V1.0
 * @author: lzq
 *
 */
public interface IGradedEnterpriseProcessService extends IService<GradedEnterpriseProcess> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<GradedEnterpriseProcessVo> pages(GradedEnterpriseProcessPageParam pageParam);

    /**
     * 列表
     *
     * @param qyid 参数
     * @return 列表
     */
    List<GradedEnterpriseProcessVo> list(String qyid);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    GradedEnterpriseProcessVo detail(String id);

    /**
     * 批量新增或修改
     *
     * @param params 参数
     */
    Boolean saveOrUpdateBatchData(List<GradedEnterpriseProcessSaveParam> params);

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
    String saveData(GradedEnterpriseProcessSaveParam param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateData(GradedEnterpriseProcessSaveParam param);

    /**
     * 删除
     *
     * @param ids 参数
     * @return 主键ID
     */
    Boolean removeData(List<String> ids);

}