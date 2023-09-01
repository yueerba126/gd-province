package com.sydata.monitoring.service.impl;

import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.entity.MonitoringStatisticsIncomeExpenses;
import com.sydata.monitoring.service.IMonitoringStatisticsIncomeExpensesService;
import com.sydata.monitoring.vo.MonitoringStatisticsIncomeExpensesVO;
import com.sydata.monitoring.mapper.MonitoringStatisticsIncomeExpensesMapper;
import com.sydata.monitoring.vo.MonitoringStatisticsProcessingRotationVO;
import com.sydata.organize.security.UserSecurity;
import org.springframework.util.CollectionUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

/**
 * <p>
 * 流通检测-粮油收支平衡数据 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Service
public class MonitoringStatisticsIncomeExpensesServiceImpl extends ServiceImpl<MonitoringStatisticsIncomeExpensesMapper, MonitoringStatisticsIncomeExpenses> implements IMonitoringStatisticsIncomeExpensesService {

    @Resource
    private MonitoringStatisticsIncomeExpensesMapper monitoringStatisticsIncomeExpensesMapper;


    @Override
    public Page<MonitoringStatisticsIncomeExpensesVO> page(MonitoringStatisticsIncomeExpensesQueryDTO queryDTO) {
        Page<MonitoringStatisticsIncomeExpenses> page = lambdaQuery()
                .page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()));

        // 获取VO分页对象
        Page<MonitoringStatisticsIncomeExpensesVO> voPage = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize(), page.getTotal());

        List<MonitoringStatisticsIncomeExpenses> records = page.getRecords();

        if (CollectionUtils.isEmpty(records)) {
            return voPage;
        }

        List<MonitoringStatisticsIncomeExpensesVO> voList = records.stream()
                .map(MonitoringStatisticsIncomeExpensesVO::new).collect(Collectors.toList());

        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public MonitoringStatisticsIncomeExpensesVO detailById(String id) {
        MonitoringStatisticsIncomeExpenses entity = getById(id);
        if (entity == null) {
            return null;
        }
        return new MonitoringStatisticsIncomeExpensesVO(entity);
    }

    @Override
    public Boolean edit(MonitoringStatisticsIncomeExpensesEditDTO editDTO) {

        if (editDTO.getId() == null) {
            editDTO.setCreateBy(UserSecurity.userName());
            editDTO.setCreateTime(LocalDateTime.now());
            editDTO.setUpdateBy(UserSecurity.userName());
            editDTO.setUpdateTime(LocalDateTime.now());
            return save(editDTO);
        }

       editDTO.setUpdateBy(UserSecurity.userName());
       editDTO.setUpdateTime(LocalDateTime.now());

       return updateById(editDTO);
    }

    @Override
    public Boolean add(MonitoringStatisticsIncomeExpensesAddDTO addDTO) {
        addDTO.setCreateBy(UserSecurity.userName());
        addDTO.setCreateTime(LocalDateTime.now());
        addDTO.setUpdateBy(UserSecurity.userName());
        addDTO.setUpdateTime(LocalDateTime.now());
        addDTO.setCountryId(UserSecurity.loginUser().getCountryId());
        return save(addDTO);
    }

    @Override
    public List<MonitoringStatisticsIncomeExpensesVO> getByStatisticsId(String statisticsId) {
        MonitoringStatisticsIncomeExpensesQueryDTO queryDTO = new MonitoringStatisticsIncomeExpensesQueryDTO(1, Integer.MAX_VALUE);
        Page<MonitoringStatisticsIncomeExpensesVO> page = page(queryDTO);
        return page.getRecords();
    }

    @Override
    public Boolean delete(MonitoringStatisticsIncomeExpensesDeleteDTO deleteDTO) {
        List<String> ids = deleteDTO.getIds();

        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        return removeByIds(ids);
    }

}
