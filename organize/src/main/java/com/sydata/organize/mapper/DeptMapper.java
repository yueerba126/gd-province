package com.sydata.organize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.organize.domain.Dept;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;

/**
 * 组织架构-部门Mapper接口
 *
 * @author lzq
 * @date 2022-11-16
 */
@DataPermissionExclude
public interface DeptMapper extends BaseMapper<Dept> {

}