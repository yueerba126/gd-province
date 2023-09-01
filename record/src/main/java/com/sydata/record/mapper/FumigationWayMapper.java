package com.sydata.record.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.record.domain.FumigationWay;

/**
 * 备案管理-熏蒸方式Mapper接口
 *
 * @author system
 * @date 2022-12-10
 */
@DataPermissionExclude
public interface FumigationWayMapper extends BaseMapper<FumigationWay> {
}
