package com.sydata.safe.asess.mapper;

import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.safe.asess.domain.CheckGroupPersonnel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 实地抽查人员Mapper接口
 *
 * @author system
 * @date 2023-02-13
 */
@DataPermissionExclude
public interface CheckGroupPersonnelMapper extends BaseMapper<CheckGroupPersonnel> {

}