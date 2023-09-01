package com.sydata.reserve.scale.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.reserve.scale.domain.PlanReserveScale;
import com.sydata.reserve.scale.dto.PlanReserveScaleDto;
import com.sydata.reserve.scale.param.PlanReserveScalePageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 储备计划管理-储备规模Mapper接口
 *
 * @author fuql
 * @date 2023-05-17
 */
@DataPermissionExclude
public interface PlanReserveScaleMapper extends BaseMapper<PlanReserveScale> {

    /**
     * 查询储备规模导出基础数据
     *
     * @return 储备规模导出dto
     */
    List<PlanReserveScaleDto> queryScaleDtos();

    /**
     * 分页查询储备规模数据
     *
     * @param page page
     * @param param param
     * @return 储备规模数据
     */
    Page<PlanReserveScale> pageScaleData(Page page, @Param("param") PlanReserveScalePageParam param);
}