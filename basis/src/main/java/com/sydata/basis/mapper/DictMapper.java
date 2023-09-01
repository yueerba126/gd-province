package com.sydata.basis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.basis.domain.Dict;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;

/**
 * 基础信息-字典Mapper接口
 *
 * @author lzq
 * @date 2022-07-26
 */
@DataPermissionExclude
public interface DictMapper extends BaseMapper<Dict> {
}