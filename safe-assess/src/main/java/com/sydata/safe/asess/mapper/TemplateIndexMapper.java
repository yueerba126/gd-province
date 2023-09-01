package com.sydata.safe.asess.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.safe.asess.domain.TemplateIndex;

/**
 * 粮食安全考核-考核指标Mapper接口
 *
 * @author system
 * @date 2023-02-13
 */
@DataPermissionExclude
public interface TemplateIndexMapper extends BaseMapper<TemplateIndex> {

}