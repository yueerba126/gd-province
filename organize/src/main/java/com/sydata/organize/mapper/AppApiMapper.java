package com.sydata.organize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.organize.domain.AppApi;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;

/**
 * 组织架构-app对接应用Mapper接口
 *
 * @author lzq
 * @date 2022-10-21
 */
@DataPermissionExclude
public interface AppApiMapper extends BaseMapper<AppApi> {

}