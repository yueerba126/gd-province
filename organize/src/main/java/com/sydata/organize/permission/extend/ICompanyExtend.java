package com.sydata.organize.permission.extend;

import java.util.List;

/**
 * @author lzq
 * @description 单位扩展接口
 * @date 2022/12/6 15:18
 */
public interface ICompanyExtend {

    /**
     * 获取下级单位ID列表
     *
     * @param companyId 单位ID
     * @return 下级单位ID列表
     */
    List<String> sonIds(String companyId);
}
