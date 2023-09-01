package com.sydata.monitoring.service.impl;

import com.sydata.monitoring.entity.MonitoringStatisticsStorageFacilities;
import com.sydata.monitoring.service.IMonitoringStatisticsStorageFacilitiesService;
import com.sydata.monitoring.dto.MonitoringStatisticsStorageFacilitiesDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStorageFacilitiesQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStorageFacilitiesEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStorageFacilitiesAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsStorageFacilitiesVO;
import com.sydata.monitoring.mapper.MonitoringStatisticsStorageFacilitiesMapper;
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
 * 流通检测-仓储设施 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Service
public class MonitoringStatisticsStorageFacilitiesServiceImpl extends ServiceImpl<MonitoringStatisticsStorageFacilitiesMapper, MonitoringStatisticsStorageFacilities> implements IMonitoringStatisticsStorageFacilitiesService {

    @Resource
    private MonitoringStatisticsStorageFacilitiesMapper monitoringStatisticsStorageFacilitiesMapper;


    @Override
    public Page<MonitoringStatisticsStorageFacilitiesVO> page(MonitoringStatisticsStorageFacilitiesQueryDTO queryDTO) {
        Page<MonitoringStatisticsStorageFacilities> page = lambdaQuery()
                .page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()));

        // 获取VO分页对象
        Page<MonitoringStatisticsStorageFacilitiesVO> voPage = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize(), page.getTotal());

        List<MonitoringStatisticsStorageFacilities> records = page.getRecords();

        if (CollectionUtils.isEmpty(records)) {
            return voPage;
        }

        List<MonitoringStatisticsStorageFacilitiesVO> voList = records.stream()
                .map(MonitoringStatisticsStorageFacilitiesVO::new).collect(Collectors.toList());

        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public MonitoringStatisticsStorageFacilitiesVO detailById(String id) {
        MonitoringStatisticsStorageFacilities entity = getById(id);
        if (entity == null) {
            return null;
        }
        return new MonitoringStatisticsStorageFacilitiesVO(entity);
    }

    @Override
    public Boolean edit(MonitoringStatisticsStorageFacilitiesEditDTO editDTO) {

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
    public Boolean add(MonitoringStatisticsStorageFacilitiesAddDTO addDTO) {
        addDTO.setCreateBy(UserSecurity.userName());
        addDTO.setCreateTime(LocalDateTime.now());
        addDTO.setUpdateBy(UserSecurity.userName());
        addDTO.setUpdateTime(LocalDateTime.now());
        addDTO.setCountryId(UserSecurity.loginUser().getCountryId());
        return save(addDTO);
    }

    @Override
    public MonitoringStatisticsStorageFacilitiesVO getByStatisticsId(String statisticsId) {
        MonitoringStatisticsStorageFacilitiesQueryDTO queryDTO = new MonitoringStatisticsStorageFacilitiesQueryDTO(1, Integer.MAX_VALUE);

        Page<MonitoringStatisticsStorageFacilitiesVO> page = page(queryDTO);

        List<MonitoringStatisticsStorageFacilitiesVO> records = page.getRecords();

        if (CollectionUtils.isEmpty(records)) {
            return null;
        }

        return records.get(0);
    }

    @Override
    public Boolean delete(MonitoringStatisticsStorageFacilitiesDeleteDTO deleteDTO) {
        List<String> ids = deleteDTO.getIds();

        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        return removeByIds(ids);
    }

}
