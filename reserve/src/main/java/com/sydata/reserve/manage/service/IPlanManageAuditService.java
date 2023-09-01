package com.sydata.reserve.manage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.reserve.manage.domain.PlanManageAudit;
import com.sydata.reserve.manage.param.PlanManageAuditPageParam;
import com.sydata.reserve.manage.param.PlanManageAuditParam;
import com.sydata.reserve.manage.vo.PlanManageAuditVo;

/**
 * 轮换计划审核Service接口
 *
 * @author fuql
 * @date 2023-05-31
 */
public interface IPlanManageAuditService extends IService<PlanManageAudit> {

    /**
     * 分页查询轮换计划审核
     *
     * @param param 参数
     * @return 轮换计划审核数据
     */
    Page<PlanManageAuditVo> pageData(PlanManageAuditPageParam param);


    /**
     * 审核轮换计划
     *
     * @param param 参数
     * @return 操作状态
     */
    Boolean audit(PlanManageAuditParam param);
}
