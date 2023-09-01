package com.sydata.reserve.layout.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.framework.util.StringUtils;
import com.sydata.manage.service.IQualityInspectionService;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.reserve.layout.mapper.PersonnelWorkloadMapper;
import com.sydata.reserve.layout.param.MonthlyReportPageParam;
import com.sydata.reserve.layout.param.PersonnelWorkloadPageParam;
import com.sydata.reserve.layout.service.IPersonnelWorkloadService;
import com.sydata.reserve.layout.vo.MonthlyReportVo;
import com.sydata.reserve.layout.vo.PersonnelWorkloadVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;

/**
 * 粮食质量-质检人员工作量统计Service业务层处理
 *
 * @author lzq
 * @date 2022-07-08
 */
@Service("personnelWorkloadService")
public class PersonnelWorkloadServiceImpl implements IPersonnelWorkloadService {

    @Resource
    private PersonnelWorkloadMapper personnelWorkloadMapper;


    @Override
    public Page<PersonnelWorkloadVo> pageWorkload(PersonnelWorkloadPageParam pageParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        Page page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        Page<PersonnelWorkloadVo> vos = personnelWorkloadMapper.pageWorkload(page , pageParam , loginUser);
        return vos;
    }

    @Override
    public Page<MonthlyReportVo> monthlyReport(MonthlyReportPageParam pageParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        Page page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        LocalDate begin = getLocalDate(pageParam.getBeginDate());
        LocalDate end = getLocalDate(pageParam.getEndDate());
        LocalDate beginDate = Objects.isNull(begin) ? null : LocalDate.of(begin.getYear(), begin.getMonth() , 1);
        LocalDate endDate = Objects.isNull(end) ? null : end.with(TemporalAdjusters.lastDayOfMonth());
        pageParam.setEndQueryDate(endDate);
        pageParam.setBeginQueryDate(beginDate);
        Page<MonthlyReportVo> reportVoPage  = personnelWorkloadMapper.monthlyReport(page , pageParam , loginUser);
        return reportVoPage;
    }

    private LocalDate getLocalDate(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();
        return LocalDate.parse(date , formatter);
    }

}