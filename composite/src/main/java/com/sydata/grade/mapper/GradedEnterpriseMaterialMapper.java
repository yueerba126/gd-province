/**
 * @filename:GradedEnterpriseMaterialDao 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import org.apache.ibatis.annotations.Mapper;
import com.sydata.grade.domain.GradedEnterpriseMaterial;

/**
 * @Description:TODO(等级粮库评定管理-企业申报证明材料数据访问层)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@Mapper
@DataPermissionExclude
public interface GradedEnterpriseMaterialMapper extends BaseMapper<GradedEnterpriseMaterial> {

}
