package com.sydata.monitoring.service;

import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.entity.GoodProduceCompany;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.vo.GoodProduceCompanyVO;

/**
 * <p>
 * 流通检测-放心粮油企业 服务类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-25
 */
public interface IGoodProduceCompanyService extends IService<GoodProduceCompany> {

    /**
     * 分页查询
     *
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    Page<GoodProduceCompanyVO> pagination(GoodProduceCompanyQueryDTO queryDTO);

    /**
     * 查询明细
     *
     * @param id id
     * @return 结果
     */
    GoodProduceCompanyVO detailById(String id);

    /**
     * 修改
     *
     * @param editDTO 修改参数
     * @return 结果
     */
    Boolean edit(GoodProduceCompanyEditDTO editDTO);

    /**
     * 删除
     *
     * @param deleteDTO 删除参数
     * @return 删除结果
     */
    Boolean delete(GoodProduceCompanyDeleteDTO deleteDTO);

    /**
     * 新增
     *
     * @param addDTO 新增参数
     * @return 新增结果
     */
    Boolean add(GoodProduceCompanyAddDTO addDTO);

    /**
     * 审核
     *
     * @param auditDTO 审核参数
     * @return 结果
     */
    Boolean audit(GoodProduceCompanyAuditDTO auditDTO);
}
