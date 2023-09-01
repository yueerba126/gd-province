package com.sydata.manage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.manage.domain.SafetyCheck;
import com.sydata.manage.param.SafetyCheckPageParam;
import com.sydata.manage.vo.SafetyCheckVo;

import java.util.Collection;

/**
 * 安全管理接口
 *
 * @author lzq
 * @date 2022/8/19 11:26
 */
public interface ISafetyCheckService extends IService<SafetyCheck> {

    /**
     * 分页查询
     *
     * @param pageParam 分页参数
     * @return Page<SafetyCheckVO>
     */
    Page<SafetyCheckVo> pages(SafetyCheckPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    SafetyCheckVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
