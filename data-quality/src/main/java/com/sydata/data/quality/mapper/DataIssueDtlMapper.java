package com.sydata.data.quality.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.data.quality.domain.DataIssueDtl;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;

/**
 * 数据质量-数据问题明细Mapper接口
 *
 * @author system
 * @date 2023-04-18
 */
@DataPermissionExclude
public interface DataIssueDtlMapper extends BaseMapper<DataIssueDtl> {

    /**
     * 清库表数据
     */
    void clear();
}