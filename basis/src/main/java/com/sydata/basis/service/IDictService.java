package com.sydata.basis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.basis.domain.Dict;
import com.sydata.basis.param.DictPageParam;
import com.sydata.basis.param.DictRemoveParam;
import com.sydata.basis.vo.DictTreeVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;
import java.util.List;

/**
 * 基础信息-字典Service接口
 *
 * @author lzq
 * @date 2022-07-26
 */
public interface IDictService extends IService<Dict> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<Dict> pages(DictPageParam pageParam);

    /**
     * 字典列表
     *
     * @param dictType 字典类型
     * @return 字典列表
     */
    List<Dict> listByDictType(String dictType);

    /**
     * 树列表
     *
     * @param dictType 字典类型
     * @return 树列表
     */
    List<DictTreeVo> treeByDictType(String dictType);

    /**
     * 删除字段数据
     *
     * @param removeParam 字典删除参数
     * @return 删除结果
     */
    boolean remove(DictRemoveParam removeParam);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
