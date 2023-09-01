package com.sydata.record.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.record.domain.FumigationDtl;

/**
 * 备案管理-熏蒸明细Mapper接口
 *
 * @author system
 * @date 2022-12-10
 */
@DataPermissionExclude
public interface FumigationDtlMapper extends BaseMapper<FumigationDtl> {

}