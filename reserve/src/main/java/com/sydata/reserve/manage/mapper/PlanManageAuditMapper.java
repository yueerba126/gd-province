package com.sydata.reserve.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.framework.orm.annotation.PageOptimizationExclude;
import com.sydata.reserve.manage.domain.PlanManageAudit;
import com.sydata.reserve.manage.param.PlanManageAuditPageParam;
import com.sydata.reserve.manage.vo.PlanManageAuditVo;
import org.apache.ibatis.annotations.Param;

/**
 * 轮换计划审核Mapper接口
 *
 * @author fuql
 * @date 2023-05-31
 */
public interface PlanManageAuditMapper extends BaseMapper<PlanManageAudit> {

    /**
     * 分页查询轮换计划审核
     *
     * @param page page
     * @param param 参数
     * @return 轮换计划审核
     */
    @PageOptimizationExclude
    Page<PlanManageAuditVo> pageData(Page page, @Param("param") PlanManageAuditPageParam param);
}