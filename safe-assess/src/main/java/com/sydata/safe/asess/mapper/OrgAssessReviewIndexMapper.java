package com.sydata.safe.asess.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.safe.asess.domain.OrgAssessReviewIndex;
import com.sydata.safe.asess.param.OrgAssessReviewIndexSubmitParam;

import java.util.List;

/**
 * 粮食安全考核-部门自评指标Mapper接口
 *
 * @author system
 * @date 2023-02-20
 */
@DataPermissionExclude
public interface OrgAssessReviewIndexMapper extends BaseMapper<OrgAssessReviewIndex> {


    /**
     * 自评
     *
     * @param list 部门自评指标提交参数列表
     * @return 操作状态
     */
    Boolean submit(List<OrgAssessReviewIndexSubmitParam> list);

}