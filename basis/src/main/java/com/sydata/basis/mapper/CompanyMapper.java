package com.sydata.basis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.basis.domain.Company;
import com.sydata.basis.dto.CompanyQualityCheckDto;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 基础数据-企业 Mapper接口
 *
 * @author lzq
 * @date 2022-08-19
 */
@DataPermissionExclude
public interface CompanyMapper extends BaseMapper<Company> {

    /**
     * 数据质量校验
     *
     * @param ids 主键列表
     * @return 数据质量校验列表
     */
    List<CompanyQualityCheckDto> qualityCheck(@Param("ids") Collection<String> ids);
}
