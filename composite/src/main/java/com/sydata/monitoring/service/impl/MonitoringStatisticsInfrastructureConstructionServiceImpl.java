package com.sydata.monitoring.service.impl;

import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.entity.MonitoringStatisticsInfrastructureConstruction;
import com.sydata.monitoring.service.IMonitoringStatisticsInfrastructureConstructionService;
import com.sydata.monitoring.vo.MonitoringStatisticsInfrastructureConstructionVO;
import com.sydata.monitoring.mapper.MonitoringStatisticsInfrastructureConstructionMapper;
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
 * 流通检测-基础设施建设信息 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Service
public class MonitoringStatisticsInfrastructureConstructionServiceImpl extends ServiceImpl<MonitoringStatisticsInfrastructureConstructionMapper, MonitoringStatisticsInfrastructureConstruction> implements IMonitoringStatisticsInfrastructureConstructionService {

    @Resource
    private MonitoringStatisticsInfrastructureConstructionMapper monitoringStatisticsInfrastructureConstructionMapper;


    @Override
    public Page<MonitoringStatisticsInfrastructureConstructionVO> page(MonitoringStatisticsInfrastructureConstructionQueryDTO queryDTO) {
        Page<MonitoringStatisticsInfrastructureConstruction> page = lambdaQuery()
                .page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()));

        // 获取VO分页对象
        Page<MonitoringStatisticsInfrastructureConstructionVO> voPage = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize(), page.getTotal());

        List<MonitoringStatisticsInfrastructureConstruction> records = page.getRecords();

        if (CollectionUtils.isEmpty(records)) {
            return voPage;
        }

        List<MonitoringStatisticsInfrastructureConstructionVO> voList = records.stream()
                .map(MonitoringStatisticsInfrastructureConstructionVO::new).collect(Collectors.toList());

        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public MonitoringStatisticsInfrastructureConstructionVO detailById(String id) {
        MonitoringStatisticsInfrastructureConstruction entity = getById(id);
        if (entity == null) {
            return null;
        }
        return new MonitoringStatisticsInfrastructureConstructionVO(entity);
    }

    @Override
    public Boolean edit(MonitoringStatisticsInfrastructureConstructionEditDTO editDTO) {

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
    public Boolean add(MonitoringStatisticsInfrastructureConstructionAddDTO addDTO) {
        addDTO.setCreateBy(UserSecurity.userName());
        addDTO.setCreateTime(LocalDateTime.now());
        addDTO.setUpdateBy(UserSecurity.userName());
        addDTO.setUpdateTime(LocalDateTime.now());
        addDTO.setCountryId(UserSecurity.loginUser().getCountryId());
        return save(addDTO);
    }

    @Override
    public List<MonitoringStatisticsInfrastructureConstructionVO> getByStatisticsId(String statisticsId) {
        MonitoringStatisticsInfrastructureConstructionQueryDTO queryDTO = new MonitoringStatisticsInfrastructureConstructionQueryDTO(1, Integer.MAX_VALUE);
        Page<MonitoringStatisticsInfrastructureConstructionVO> page = page(queryDTO);
        return page.getRecords();
    }

    @Override
    public Boolean delete(MonitoringStatisticsInfrastructureConstructionDeleteDTO deleteDTO) {
        List<String> ids = deleteDTO.getIds();

        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        return removeByIds(ids);
    }

}
