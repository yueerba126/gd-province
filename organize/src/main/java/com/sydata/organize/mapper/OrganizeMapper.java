package com.sydata.organize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.organize.domain.Organize;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;

/**
 * 组织架构-组织Mapper接口
 *
 * @author lzq
 * @date 2022-06-28
 */
@DataPermissionExclude
public interface OrganizeMapper extends BaseMapper<Organize> {

}