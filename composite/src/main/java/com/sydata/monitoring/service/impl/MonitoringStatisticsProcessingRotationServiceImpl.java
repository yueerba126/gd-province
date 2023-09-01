package com.sydata.monitoring.service.impl;

import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.entity.MonitoringStatisticsProcessingRotation;
import com.sydata.monitoring.service.IMonitoringStatisticsProcessingRotationService;
import com.sydata.monitoring.vo.MonitoringStatisticsProcessingRotationVO;
import com.sydata.monitoring.mapper.MonitoringStatisticsProcessingRotationMapper;
import com.sydata.monitoring.vo.MonitoringStatisticsStorageFacilitiesVO;
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
 * 流通检测-加工轮换 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Service
public class MonitoringStatisticsProcessingRotationServiceImpl extends ServiceImpl<MonitoringStatisticsProcessingRotationMapper, MonitoringStatisticsProcessingRotation> implements IMonitoringStatisticsProcessingRotationService {

    @Resource
    private MonitoringStatisticsProcessingRotationMapper monitoringStatisticsProcessingRotationMapper;


    @Override
    public Page<MonitoringStatisticsProcessingRotationVO> page(MonitoringStatisticsProcessingRotationQueryDTO queryDTO) {
        Page<MonitoringStatisticsProcessingRotation> page = lambdaQuery()
                .page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()));

        // 获取VO分页对象
        Page<MonitoringStatisticsProcessingRotationVO> voPage = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize(), page.getTotal());

        List<MonitoringStatisticsProcessingRotation> records = page.getRecords();

        if (CollectionUtils.isEmpty(records)) {
            return voPage;
        }

        List<MonitoringStatisticsProcessingRotationVO> voList = records.stream()
                .map(MonitoringStatisticsProcessingRotationVO::new).collect(Collectors.toList());

        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public MonitoringStatisticsProcessingRotationVO detailById(String id) {
        MonitoringStatisticsProcessingRotation entity = getById(id);
        if (entity == null) {
            return null;
        }
        return new MonitoringStatisticsProcessingRotationVO(entity);
    }

    @Override
    public Boolean edit(MonitoringStatisticsProcessingRotationEditDTO editDTO) {

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
    public Boolean add(MonitoringStatisticsProcessingRotationAddDTO addDTO) {
        addDTO.setCreateBy(UserSecurity.userName());
        addDTO.setCreateTime(LocalDateTime.now());
        addDTO.setUpdateBy(UserSecurity.userName());
        addDTO.setUpdateTime(LocalDateTime.now());
        addDTO.setCountryId(UserSecurity.loginUser().getCountryId());
        return save(addDTO);
    }

    @Override
    public List<MonitoringStatisticsProcessingRotationVO> getByStatisticsId(String statisticsId) {
        MonitoringStatisticsProcessingRotationQueryDTO queryDTO = new MonitoringStatisticsProcessingRotationQueryDTO(1, Integer.MAX_VALUE);
        Page<MonitoringStatisticsProcessingRotationVO> page = page(queryDTO);
        return page.getRecords();
    }

    @Override
    public Boolean delete(MonitoringStatisticsProcessingRotationDeleteDTO deleteDTO) {
        List<String> ids = deleteDTO.getIds();

        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        return removeByIds(ids);
    }

}
