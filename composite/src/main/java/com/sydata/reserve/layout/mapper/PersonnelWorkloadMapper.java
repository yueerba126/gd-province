package com.sydata.reserve.layout.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.organize.security.LoginUser;
import com.sydata.reserve.layout.param.MonthlyReportPageParam;
import com.sydata.reserve.layout.param.PersonnelWorkloadPageParam;
import com.sydata.reserve.layout.vo.MonthlyReportVo;
import com.sydata.reserve.layout.vo.PersonnelWorkloadVo;
import org.apache.ibatis.annotations.Param;

/**
 * 粮食质量-质检人员工作量统计 Mapper接口
 *
 * @author lzq
 * @date 2022-08-19
 */
public interface PersonnelWorkloadMapper {

    /**
     * 粮食质量-质检人员工作量统计
     *
     * @param param     param
     * @param pageParam pageParam
     * @return 工作量统计
     */
    Page<PersonnelWorkloadVo> pageWorkload(Page page, @Param("pageParam") PersonnelWorkloadPageParam pageParam, @Param("param") LoginUser param);

    /**
     * 粮食质量-检测业务月报表
     *
     * @param param     param
     * @param pageParam pageParam
     * @return 工作量统计
     */
    Page<MonthlyReportVo> monthlyReport(Page page, @Param("pageParam") MonthlyReportPageParam pageParam, @Param("param") LoginUser param);
}
