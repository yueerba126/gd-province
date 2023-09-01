package com.sydata.framework.databind.handle.strategy;


import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;

/**
 * 数据绑定查询策略
 *
 * @author zhoucl
 * @date 2021/4/21 14:25
 */
public interface IDataQueryService {

    /**
     * 具体数据查询
     *
     * @param dataBindQuerys
     * @return
     */
    void listByQueryColumn(Collection<DataBindQuery> dataBindQuerys);
}
