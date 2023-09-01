package com.sydata.monitoring.service.impl;

import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.entity.MonitoringStatisticsScienceInfo;
import com.sydata.monitoring.service.IMonitoringStatisticsScienceInfoService;
import com.sydata.monitoring.vo.MonitoringStatisticsProcessingRotationVO;
import com.sydata.monitoring.vo.MonitoringStatisticsScienceInfoVO;
import com.sydata.monitoring.mapper.MonitoringStatisticsScienceInfoMapper;
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
 * 流通检测-粮油科技信息 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Service
public class MonitoringStatisticsScienceInfoServiceImpl extends ServiceImpl<MonitoringStatisticsScienceInfoMapper, MonitoringStatisticsScienceInfo> implements IMonitoringStatisticsScienceInfoService {

    @Resource
    private MonitoringStatisticsScienceInfoMapper monitoringStatisticsScienceInfoMapper;


    @Override
    public Page<MonitoringStatisticsScienceInfoVO> page(MonitoringStatisticsScienceInfoQueryDTO queryDTO) {
        Page<MonitoringStatisticsScienceInfo> page = lambdaQuery()
                .page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()));

        // 获取VO分页对象
        Page<MonitoringStatisticsScienceInfoVO> voPage = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize(), page.getTotal());

        List<MonitoringStatisticsScienceInfo> records = page.getRecords();

        if (CollectionUtils.isEmpty(records)) {
            return voPage;
        }

        List<MonitoringStatisticsScienceInfoVO> voList = records.stream()
                .map(MonitoringStatisticsScienceInfoVO::new).collect(Collectors.toList());

        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public MonitoringStatisticsScienceInfoVO detailById(String id) {
        MonitoringStatisticsScienceInfo entity = getById(id);
        if (entity == null) {
            return null;
        }
        return new MonitoringStatisticsScienceInfoVO(entity);
    }

    @Override
    public Boolean edit(MonitoringStatisticsScienceInfoEditDTO editDTO) {

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
    public Boolean add(MonitoringStatisticsScienceInfoAddDTO addDTO) {
        addDTO.setCreateBy(UserSecurity.userName());
        addDTO.setCreateTime(LocalDateTime.now());
        addDTO.setUpdateBy(UserSecurity.userName());
        addDTO.setUpdateTime(LocalDateTime.now());
        addDTO.setCountryId(UserSecurity.loginUser().getCountryId());
        return save(addDTO);
    }

    @Override
    public List<MonitoringStatisticsScienceInfoVO> getByStatisticsId(String statisticsId) {
        MonitoringStatisticsScienceInfoQueryDTO queryDTO = new MonitoringStatisticsScienceInfoQueryDTO(1, Integer.MAX_VALUE);
        Page<MonitoringStatisticsScienceInfoVO> page = page(queryDTO);
        return page.getRecords();
    }

    @Override
    public Boolean delete(MonitoringStatisticsScienceInfoDeleteDTO deleteDTO) {
        List<String> ids = deleteDTO.getIds();

        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        return removeByIds(ids);
    }

}
