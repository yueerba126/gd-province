package com.sydata.data.quality.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.data.quality.domain.ReportDtl;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;

/**
 * 数据质量-报告明细Mapper接口
 *
 * @author system
 * @date 2023-04-18
 */
@DataPermissionExclude
public interface ReportDtlMapper extends BaseMapper<ReportDtl> {

}