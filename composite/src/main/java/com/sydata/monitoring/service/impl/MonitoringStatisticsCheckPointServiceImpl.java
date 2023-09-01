package com.sydata.monitoring.service.impl;

import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.service.IMonitoringStatisticsCheckPointService;
import com.sydata.monitoring.entity.MonitoringStatisticsCheckPoint;
import com.sydata.monitoring.vo.MonitoringStatisticsCheckPointVO;
import com.sydata.monitoring.mapper.MonitoringStatisticsCheckPointMapper;
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
 * 流通检测-粮食市场监测点信息 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Service
public class MonitoringStatisticsCheckPointServiceImpl extends ServiceImpl<MonitoringStatisticsCheckPointMapper, MonitoringStatisticsCheckPoint> implements IMonitoringStatisticsCheckPointService {

    @Resource
    private MonitoringStatisticsCheckPointMapper monitoringStatisticsCheckPointMapper;

    @Override
    public Page<MonitoringStatisticsCheckPointVO> page(MonitoringStatisticsCheckPointQueryDTO queryDTO) {

        Page<MonitoringStatisticsCheckPoint> page = lambdaQuery()
                .page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()));

        // 获取VO分页对象
        Page<MonitoringStatisticsCheckPointVO> voPage = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize(), page.getTotal());

        List<MonitoringStatisticsCheckPoint> records = page.getRecords();

        if (CollectionUtils.isEmpty(records)) {
            return voPage;
        }

        List<MonitoringStatisticsCheckPointVO> voList = records.stream()
                .map(MonitoringStatisticsCheckPointVO::new).collect(Collectors.toList());

        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public MonitoringStatisticsCheckPointVO detailById(String id) {
        MonitoringStatisticsCheckPoint entity = getById(id);
        if (entity == null) {
            return null;
        }
        return new MonitoringStatisticsCheckPointVO(entity);
    }

    @Override
    public Boolean edit(MonitoringStatisticsCheckPointEditDTO editDTO) {
        String statisticId = editDTO.getStatisticId();

        // 根据统计ID删除绑定的监测点：一个企业只能有一个监测点
        lambdaUpdate()
                .eq(MonitoringStatisticsCheckPoint::getStatisticId, statisticId)
                .remove();

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
    public Boolean add(MonitoringStatisticsCheckPointAddDTO addDTO) {
        String statisticId = addDTO.getStatisticId();

        // 根据统计ID删除绑定的监测点：一个企业只能有一个监测点
        lambdaUpdate()
                .eq(MonitoringStatisticsCheckPoint::getStatisticId, statisticId)
                .remove();

        addDTO.setCreateBy(UserSecurity.userName());
        addDTO.setCreateTime(LocalDateTime.now());
        addDTO.setUpdateBy(UserSecurity.userName());
        addDTO.setUpdateTime(LocalDateTime.now());
        addDTO.setCountryId(UserSecurity.loginUser().getCountryId());
        return save(addDTO);
    }

    @Override
    public List<MonitoringStatisticsCheckPointVO> getByStatisticsId(String statisticsId) {
        MonitoringStatisticsCheckPointQueryDTO queryDTO = new MonitoringStatisticsCheckPointQueryDTO(1, Integer.MAX_VALUE);
        Page<MonitoringStatisticsCheckPointVO> page = page(queryDTO);
        return page.getRecords();
    }

    @Override
    public Boolean delete(MonitoringStatisticsCheckPointDeleteDTO deleteDTO) {
        List<String> ids = deleteDTO.getIds();

        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        return removeByIds(ids);
    }

}
