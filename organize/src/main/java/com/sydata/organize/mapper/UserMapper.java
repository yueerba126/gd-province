package com.sydata.organize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.framework.orm.annotation.PageOptimizationExclude;
import com.sydata.organize.domain.User;
import com.sydata.organize.param.UserPageParam;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.organize.vo.UserVo;
import org.apache.ibatis.annotations.Param;

/**
 * 组织架构-用户Mapper接口
 *
 * @author lzq
 * @date 2022-06-28
 */
@DataPermissionExclude
public interface UserMapper extends BaseMapper<User> {


    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    @PageOptimizationExclude
    Page<UserVo> pages(@Param("page") Page page, @Param("param") UserPageParam pageParam);
}