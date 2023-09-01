package com.sydata.reserve.layout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.reserve.layout.domain.TestingInstitutes;

/**
 * 储备布局地理信息-质检机构 Mapper接口
 *
 * @author lzq
 * @date 2022-08-19
 */
@DataPermissionExclude
public interface TestingInstitutesMapper extends BaseMapper<TestingInstitutes> {

}
