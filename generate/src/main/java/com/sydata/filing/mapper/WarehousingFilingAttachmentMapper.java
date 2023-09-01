package com.sydata.filing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.filing.domain.WarehousingFilingAttachment;

/**   
 * @Description:TODO(仓储备案-仓储附件数据访问层)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
@DataPermissionExclude
@Mapper
public interface WarehousingFilingAttachmentMapper extends BaseMapper<WarehousingFilingAttachment> {
	
}
