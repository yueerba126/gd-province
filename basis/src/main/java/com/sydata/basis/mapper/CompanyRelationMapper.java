package com.sydata.basis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.basis.domain.CompanyRelation;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;

/**
 * 单位信息关系Mapper接口
 *
 * @author lzq
 * @date 2022-12-06
 */
@DataPermissionExclude
public interface CompanyRelationMapper extends BaseMapper<CompanyRelation> {

}