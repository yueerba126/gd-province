package com.sydata.framework.databind.handle.strategy;



import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;
import java.util.Map;

/**
 * 数据绑定查询策略
 *
 * @author zhoucl
 * @date 2021/4/21 14:25
 */
public interface IDataBindStrategy {

    /**
     * 通过数据id获取数据，该方法实现时需要考虑缓存问题，不然会拖慢业务响应速度
     *
     * @param dataBindQuerys 数据查询对象
     * @return key 是数据id；value是具体数据对象
     */
    Map<?, ?> queryData(Collection<DataBindQuery> dataBindQuerys);
}
