package com.sydata.reserve.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.reserve.manage.domain.PlanManageAuditDtl;
import com.sydata.reserve.manage.mapper.PlanManageAuditDtlMapper;
import com.sydata.reserve.manage.service.IPlanManageAuditDtlService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 轮换计划审核详情Service业务层处理
 *
 * @author fuql
 * @date 2023-05-31
 */
@Service("planManageAuditDtlService")
public class PlanManageAuditDtlServiceImpl extends ServiceImpl<PlanManageAuditDtlMapper, PlanManageAuditDtl> implements IPlanManageAuditDtlService {

    @Resource
    private PlanManageAuditDtlMapper planManageAuditDtlMapper;


}
