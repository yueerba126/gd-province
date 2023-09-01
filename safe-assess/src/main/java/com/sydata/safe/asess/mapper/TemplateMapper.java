package com.sydata.safe.asess.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.safe.asess.domain.Template;
import org.apache.ibatis.annotations.Param;

/**
 * 粮食安全考核-考核模板Mapper接口
 *
 * @author system
 * @date 2023-02-13
 */
public interface TemplateMapper extends BaseMapper<Template> {

    /**
     * 操作地市已提交数量
     *
     * @param id    模板ID
     * @param count 操作数量
     * @return 操作状态
     */
    Boolean operationRegionSubmitCount(@Param("id") String id, @Param("count") int count);

    /**
     * 操作地市已考核数量
     *
     * @param id    模板ID
     * @param count 操作数量
     * @return 操作状态
     */
    Boolean operationRegionAssessCount(@Param("id") String id, @Param("count") int count);

    /**
     * 操作部门已完成分配数量
     *
     * @param id    模板ID
     * @param count 操作数量
     * @return 操作状态
     */
    Boolean operationDeptAllotCount(@Param("id") String id, @Param("count") int count);

    /**
     * 操作部门已完成考核数量
     *
     * @param id    模板ID
     * @param count 操作数量
     * @return 操作状态
     */
    Boolean operationDeptAssessCount(@Param("id") String id, @Param("count") int count);
}