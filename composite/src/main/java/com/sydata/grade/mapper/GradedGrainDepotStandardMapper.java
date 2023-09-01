/**
 * @filename:GradedGrainDepotStandardDao 2023年05月17日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import org.apache.ibatis.annotations.Mapper;
import com.sydata.grade.domain.GradedGrainDepotStandard;

/**
 * @Description:TODO(等级粮库评定管理-等级粮库评定标准数据访问层)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@Mapper
@DataPermissionExclude
public interface GradedGrainDepotStandardMapper extends BaseMapper<GradedGrainDepotStandard> {

}
