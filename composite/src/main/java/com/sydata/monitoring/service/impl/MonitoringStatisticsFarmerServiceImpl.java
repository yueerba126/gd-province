package com.sydata.monitoring.service.impl;

import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.entity.MonitoringStatisticsFarmer;
import com.sydata.monitoring.service.IMonitoringStatisticsFarmerService;
import com.sydata.monitoring.vo.MonitoringStatisticsFarmerVO;
import com.sydata.monitoring.mapper.MonitoringStatisticsFarmerMapper;
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
 * 流通检测-居民农户信息 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Service
public class MonitoringStatisticsFarmerServiceImpl extends ServiceImpl<MonitoringStatisticsFarmerMapper, MonitoringStatisticsFarmer> implements IMonitoringStatisticsFarmerService {

    @Resource
    private MonitoringStatisticsFarmerMapper monitoringStatisticsFarmerMapper;


    @Override
    public Page<MonitoringStatisticsFarmerVO> page(MonitoringStatisticsFarmerQueryDTO queryDTO) {

        Page<MonitoringStatisticsFarmer> page = lambdaQuery()
                .page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()));

        // 获取VO分页对象
        Page<MonitoringStatisticsFarmerVO> voPage = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize(), page.getTotal());

        List<MonitoringStatisticsFarmer> records = page.getRecords();

        if (CollectionUtils.isEmpty(records)) {
            return voPage;
        }

        List<MonitoringStatisticsFarmerVO> voList = records.stream()
                .map(MonitoringStatisticsFarmerVO::new).collect(Collectors.toList());

        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public MonitoringStatisticsFarmerVO detailById(String id) {
        MonitoringStatisticsFarmer entity = getById(id);
        if (entity == null) {
            return null;
        }
        return new MonitoringStatisticsFarmerVO(entity);
    }

    @Override
    public Boolean edit(MonitoringStatisticsFarmerEditDTO editDTO) {

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
    public Boolean add(MonitoringStatisticsFarmerAddDTO addDTO) {
        addDTO.setCreateBy(UserSecurity.userName());
        addDTO.setCreateTime(LocalDateTime.now());
        addDTO.setUpdateBy(UserSecurity.userName());
        addDTO.setUpdateTime(LocalDateTime.now());
        addDTO.setCountryId(UserSecurity.loginUser().getCountryId());
        return save(addDTO);
    }

    @Override
    public List<MonitoringStatisticsFarmerVO> getByStatisticsId(String statisticsId) {
        MonitoringStatisticsFarmerQueryDTO queryDTO = new MonitoringStatisticsFarmerQueryDTO(1, Integer.MAX_VALUE);
        Page<MonitoringStatisticsFarmerVO> page = page(queryDTO);
        return page.getRecords();
    }

    @Override
    public Boolean delete(MonitoringStatisticsFarmerDeleteDTO deleteDTO) {
        List<String> ids = deleteDTO.getIds();

        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        return removeByIds(ids);
    }

}
