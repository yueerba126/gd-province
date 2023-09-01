package com.sydata.monitoring.service.impl;

import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.entity.MonitoringStatisticsProcess;
import com.sydata.monitoring.service.IMonitoringStatisticsProcessService;
import com.sydata.monitoring.vo.MonitoringStatisticsProcessVO;
import com.sydata.monitoring.mapper.MonitoringStatisticsProcessMapper;
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
 * 流通检测-粮油加工信息 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Service
public class MonitoringStatisticsProcessServiceImpl extends ServiceImpl<MonitoringStatisticsProcessMapper, MonitoringStatisticsProcess> implements IMonitoringStatisticsProcessService {

    @Resource
    private MonitoringStatisticsProcessMapper monitoringStatisticsProcessMapper;


    @Override
    public Page<MonitoringStatisticsProcessVO> page(MonitoringStatisticsProcessQueryDTO queryDTO) {
        Page<MonitoringStatisticsProcess> page = lambdaQuery()
                .page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()));

        // 获取VO分页对象
        Page<MonitoringStatisticsProcessVO> voPage = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize(), page.getTotal());

        List<MonitoringStatisticsProcess> records = page.getRecords();

        if (CollectionUtils.isEmpty(records)) {
            return voPage;
        }

        List<MonitoringStatisticsProcessVO> voList = records.stream()
                .map(MonitoringStatisticsProcessVO::new).collect(Collectors.toList());

        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public MonitoringStatisticsProcessVO detailById(String id) {
        MonitoringStatisticsProcess entity = getById(id);
        if (entity == null) {
            return null;
        }
        return new MonitoringStatisticsProcessVO(entity);
    }

    @Override
    public Boolean edit(MonitoringStatisticsProcessEditDTO editDTO) {

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
    public Boolean add(MonitoringStatisticsProcessAddDTO addDTO) {
        addDTO.setCreateBy(UserSecurity.userName());
        addDTO.setCreateTime(LocalDateTime.now());
        addDTO.setUpdateBy(UserSecurity.userName());
        addDTO.setUpdateTime(LocalDateTime.now());
        addDTO.setCountryId(UserSecurity.loginUser().getCountryId());
        return save(addDTO);
    }

    @Override
    public List<MonitoringStatisticsProcessVO> getByStatisticsId(String statisticsId) {
        MonitoringStatisticsProcessQueryDTO queryDTO = new MonitoringStatisticsProcessQueryDTO(1, Integer.MAX_VALUE);
        Page<MonitoringStatisticsProcessVO> page = page(queryDTO);
        return page.getRecords();
    }

    @Override
    public Boolean delete(MonitoringStatisticsProcessDeleteDTO deleteDTO) {
        List<String> ids = deleteDTO.getIds();

        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        return removeByIds(ids);
    }

}
