package com.sydata.monitoring.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.entity.GoodCompany;
import com.sydata.monitoring.vo.GoodCompanyVO;

/**
 * <p>
 * 流通检测-好粮油企业 服务类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-24
 */
public interface IGoodCompanyService extends IService<GoodCompany> {

    /**
     * 分页查询
     *
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    Page<GoodCompanyVO> pagination(GoodCompanyQueryDTO queryDTO);

    /**
     * 查询明细
     *
     * @param id id
     * @return 结果
     */
    GoodCompanyVO detailById(String id);

    /**
     * 修改
     *
     * @param editDTO 修改参数
     * @return 结果
     */
    Boolean edit(GoodCompanyEditDTO editDTO);

    /**
     * 删除
     *
     * @param deleteDTO 删除参数
     * @return 删除结果
     */
    Boolean delete(GoodCompanyDeleteDTO deleteDTO);

    /**
     * 新增
     *
     * @param addDTO 新增参数
     * @return 新增结果
     */
    Boolean add(GoodCompanyAddDTO addDTO);

    /**
     * 审核
     *
     * @param auditDTO 审核参数
     * @return 结果
     */
    Boolean aduit(GoodCompanyAuditDTO auditDTO);
}
