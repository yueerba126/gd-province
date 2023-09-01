/**
 * @filename:GradedExpertManageDao 2023年05月25日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import org.apache.ibatis.annotations.Mapper;
import com.sydata.grade.domain.GradedExpertManage;

/**   
 * @Description:TODO(等级粮库评定管理-专家管理数据访问层)
 *
 * @version: V1.0
 * @author: lzq
 * 
 */
@Mapper
@DataPermissionExclude
public interface GradedExpertManageMapper extends BaseMapper<GradedExpertManage> {
	
}
