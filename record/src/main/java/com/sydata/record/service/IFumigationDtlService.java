package com.sydata.record.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.record.domain.FumigationDtl;

import java.util.List;

/**
 * 备案管理-熏蒸明细Service接口
 *
 * @author system
 * @date 2022-12-10
 */
public interface IFumigationDtlService extends IService<FumigationDtl> {

    /**
     * 根据熏蒸备案ID查询明细列表
     *
     * @param fumigationId 熏蒸备案ID
     * @return 明细列表
     */
    List<FumigationDtl> listByFumigationId(String fumigationId);

    /**
     * 设置熏蒸明细列表
     *
     * @param fumigationId 熏蒸备案ID
     * @param list         熏蒸明细列表
     */
    void build(String fumigationId, List<FumigationDtl> list);
}