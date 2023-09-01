package com.sydata.dostrict.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.common.file.domain.FileStorage;
import com.sydata.dostrict.personnel.domain.ApparitorSystem;
import com.sydata.dostrict.personnel.param.ApparitorSystemPageParam;
import com.sydata.dostrict.personnel.vo.ApparitorSystemVo;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import org.apache.ibatis.annotations.Param;

/**
 * 行政管理-人员制度管理Mapper接口
 *
 * @author fuql
 * @date 2023-04-24
 */
@DataPermissionExclude
public interface ApparitorSystemMapper extends BaseMapper<ApparitorSystem> {


    /**
     * 去除权限查询
     *
     * @param page 分页查询
     * @param param param
     * @return 人员制度管理
     */
    Page<ApparitorSystemVo> pageData(@Param("page") Page page , @Param("param") ApparitorSystemPageParam param);

    /**
     * 无权限查询附件
     *
     * @param fileId fileId
     * @return 附件
     */
    FileStorage queryFileStorage(@Param("fileId") String fileId);

}