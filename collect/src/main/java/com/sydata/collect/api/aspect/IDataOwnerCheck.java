package com.sydata.collect.api.aspect;

import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.organize.enums.OrganizeKindEnum;
import com.sydata.organize.security.LoginUser;

/**
 * @author lzq
 * @description 数据归属权限校验接口
 * @date 2023/5/30 15:34
 */
public interface IDataOwnerCheck {

    /**
     * 组织类型
     *
     * @return 组织类型枚举
     */
    OrganizeKindEnum type();

    /**
     * 校验数据归属
     *
     * @param apiParam API操作参数基类
     * @param login    当前登录用户
     */
    void checkDataOwner(BaseApiParam apiParam, LoginUser login);
}
