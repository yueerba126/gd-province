package com.sydata.monitoring.service.impl;

import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.entity.MonitoringStatisticsStorehouse;
import com.sydata.monitoring.service.IMonitoringStatisticsStorehouseService;
import com.sydata.monitoring.vo.MonitoringStatisticsStorehouseVO;
import com.sydata.monitoring.mapper.MonitoringStatisticsStorehouseMapper;
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
 * 流通检测-库点信息 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Service
public class MonitoringStatisticsStorehouseServiceImpl extends ServiceImpl<MonitoringStatisticsStorehouseMapper, MonitoringStatisticsStorehouse> implements IMonitoringStatisticsStorehouseService {

    @Resource
    private MonitoringStatisticsStorehouseMapper monitoringStatisticsStorehouseMapper;


    @Override
    public Page<MonitoringStatisticsStorehouseVO> page(MonitoringStatisticsStorehouseQueryDTO queryDTO) {
        Page<MonitoringStatisticsStorehouse> page = lambdaQuery()
                .page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()));

        // 获取VO分页对象
        Page<MonitoringStatisticsStorehouseVO> voPage = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize(), page.getTotal());

        List<MonitoringStatisticsStorehouse> records = page.getRecords();

        if (CollectionUtils.isEmpty(records)) {
            return voPage;
        }

        List<MonitoringStatisticsStorehouseVO> voList = records.stream()
                .map(MonitoringStatisticsStorehouseVO::new).collect(Collectors.toList());

        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public MonitoringStatisticsStorehouseVO detailById(String id) {
        MonitoringStatisticsStorehouse entity = getById(id);
        if (entity == null) {
            return null;
        }
        return new MonitoringStatisticsStorehouseVO(entity);
    }

    @Override
    public Boolean edit(MonitoringStatisticsStorehouseEditDTO editDTO) {

        if (editDTO.getId() == null) {
            editDTO.setCreateBy(UserSecurity.userName());
            editDTO.setCreateTime(LocalDateTime.now());
            editDTO.setUpdateBy(UserSecurity.userName());
            editDTO.setUpdateTime(LocalDateTime.now());
            editDTO.setCountryId(UserSecurity.loginUser().getCountryId());

            return save(editDTO);
        }

        editDTO.setUpdateBy(UserSecurity.userName());
        editDTO.setUpdateTime(LocalDateTime.now());
        editDTO.setCountryId(UserSecurity.loginUser().getCountryId());

        return updateById(editDTO);
    }

    @Override
    public Boolean add(MonitoringStatisticsStorehouseAddDTO addDTO) {
        addDTO.setCreateBy(UserSecurity.userName());
        addDTO.setCreateTime(LocalDateTime.now());
        addDTO.setUpdateBy(UserSecurity.userName());
        addDTO.setUpdateTime(LocalDateTime.now());
        addDTO.setCountryId(UserSecurity.loginUser().getCountryId());
        return save(addDTO);
    }

    @Override
    public List<MonitoringStatisticsStorehouseVO> getByStatisticsId(String statisticsId) {
        MonitoringStatisticsStorehouseQueryDTO queryDTO = new MonitoringStatisticsStorehouseQueryDTO(1, Integer.MAX_VALUE);
        Page<MonitoringStatisticsStorehouseVO> page = page(queryDTO);
        return page.getRecords();
    }

    @Override
    public Boolean delete(MonitoringStatisticsStorehouseDeleteDTO deleteDTO) {
        List<String> ids = deleteDTO.getIds();

        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        return removeByIds(ids);
    }

}
