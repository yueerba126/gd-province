package com.sydata.organize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.organize.domain.MenuSystem;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;

/**
 * @author lzq
 * @description 组织架构-菜单系统Mapper接口
 * @date 2023/5/22 15:00
 */
@DataPermissionExclude
public interface MenuSystemMapper extends BaseMapper<MenuSystem> {
}
