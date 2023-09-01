package com.sydata.safe.asess.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.safe.asess.domain.Template;
import com.sydata.safe.asess.param.*;
import com.sydata.safe.asess.vo.TemplateAllotDeptVo;
import com.sydata.safe.asess.vo.TemplateVo;

import java.util.Collection;
import java.util.List;

/**
 * 粮食安全考核-考核模板Service接口
 *
 * @author system
 * @date 2023-02-13
 */
public interface ITemplateService extends IService<Template> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<TemplateVo> page(TemplatePageParam pageParam);

    /**
     * 生成编号
     *
     * @return 唯一编号
     */
    String generateNumber();

    /**
     * 根据模板名称获取考核模板
     *
     * @param year       年份
     * @param name       模板名称
     * @param organizeId 组织ID
     * @return 考核模板
     */
    Template getByUnxName(String year, String name, String organizeId);

    /**
     * 新增
     *
     * @param saveParam 考核模板保存参数
     * @return 保存状态
     */
    Boolean add(TemplateSaveParam saveParam);


    /**
     * 复制新增
     *
     * @param copySaveParam 考核模板复制保存参数
     * @return 保存状态
     */
    Boolean copyAdd(TemplateCopySaveParam copySaveParam);


    /**
     * 修改
     *
     * @param updateParam 考核模板编辑参数
     * @return 修改状态
     */
    Boolean update(TemplateUpdateParam updateParam);

    /**
     * 获取模板分配部门列表
     *
     * @param id 模板ID
     * @return 考核模板分配部门VO
     */
    List<TemplateAllotDeptVo> allotDept(String id);

    /**
     * 分配
     *
     * @param id 模板ID
     * @return 分配结果
     */
    Boolean allot(String id);

    /**
     * 发布
     *
     * @param pushParam 考核模板发布参数
     * @return 发布结果
     */
    Boolean push(TemplatePushParam pushParam);

    /**
     * 撤回
     *
     * @param id 模板ID
     * @return 撤回结果
     */
    Boolean revoke(String id);

    /**
     * 操作地市已提交数量
     *
     * @param id    模板ID
     * @param count 操作数量
     * @return 操作状态
     */
    Boolean operationRegionSubmitCount(String id, int count);

    /**
     * 操作地市已考核数量
     *
     * @param id    模板ID
     * @param count 操作数量
     * @return 操作状态
     */
    Boolean operationRegionAssessCount(String id, int count);

    /**
     * 操作部门已完成分配数量
     *
     * @param id    模板ID
     * @param count 操作数量
     * @return 操作状态
     */
    Boolean operationDeptAllotCount(String id, int count);

    /**
     * 操作部门已完成考核数量
     *
     * @param id    模板ID
     * @param count 操作数量
     * @return 操作状态
     */
    Boolean operationDeptAssessCount(String id, int count);

    /**
     * 操作权限
     *
     * @param id 考核模板ID
     * @return 权限标识
     */
    Boolean operationAuth(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}