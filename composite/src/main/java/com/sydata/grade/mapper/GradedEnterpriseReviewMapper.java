/**
 * @filename:GradedEnterpriseReviewDao 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.grade.domain.GradedEnterpriseReview;
import com.sydata.grade.param.GradedEnterpriseReviewPageParam;
import com.sydata.grade.vo.GradedEnterpriseReviewVo;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:TODO(等级粮库评定管理-企业申报审核数据访问层)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@Mapper
@DataPermissionExclude
public interface GradedEnterpriseReviewMapper extends BaseMapper<GradedEnterpriseReview> {
    /**
     * 查询申报信息，对应的按钮，对应的流程状态
     * @param param
     * @return
     */
    Page<GradedEnterpriseReviewVo> getGradedEnterpriseReviewList(@Param("page") Page page,@Param("param") GradedEnterpriseReviewPageParam param);

    /**
     * 查询sd状态下的申报信息，对应的按钮，对应的流程状态
     * @param param
     * @return
     */
    Page<GradedEnterpriseReviewVo> getSdGradedEnterpriseReviewList(@Param("page") Page page,@Param("param") GradedEnterpriseReviewPageParam param);

    /**
     * 查询申报信息，对应的按钮，对应的流程状态
     * @param param
     * @return
     */
    List<GradedEnterpriseReviewVo> getGradedEnterpriseReviewListByIds(@Param("param") GradedEnterpriseReviewPageParam param);

    /**
     * 查询sd状态下的申报信息，对应的按钮，对应的流程状态
     * @param param
     * @return
     */
    List<GradedEnterpriseReviewVo> getSdGradedEnterpriseReviewListByIds(@Param("param") GradedEnterpriseReviewPageParam param);
}
