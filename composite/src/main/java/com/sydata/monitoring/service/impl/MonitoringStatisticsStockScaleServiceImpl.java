package com.sydata.monitoring.service.impl;

import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.entity.MonitoringStatisticsStockScale;
import com.sydata.monitoring.service.IMonitoringStatisticsStockScaleService;
import com.sydata.monitoring.vo.MonitoringStatisticsProcessingRotationVO;
import com.sydata.monitoring.vo.MonitoringStatisticsStockScaleVO;
import com.sydata.monitoring.mapper.MonitoringStatisticsStockScaleMapper;
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
 * 流通检测-库存规模 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Service
public class MonitoringStatisticsStockScaleServiceImpl extends ServiceImpl<MonitoringStatisticsStockScaleMapper, MonitoringStatisticsStockScale> implements IMonitoringStatisticsStockScaleService {

    @Resource
    private MonitoringStatisticsStockScaleMapper monitoringStatisticsStockScaleMapper;


    @Override
    public Page<MonitoringStatisticsStockScaleVO> page(MonitoringStatisticsStockScaleQueryDTO queryDTO) {
        Page<MonitoringStatisticsStockScale> page = lambdaQuery()
                .page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()));

        // 获取VO分页对象
        Page<MonitoringStatisticsStockScaleVO> voPage = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize(), page.getTotal());

        List<MonitoringStatisticsStockScale> records = page.getRecords();

        if (CollectionUtils.isEmpty(records)) {
            return voPage;
        }

        List<MonitoringStatisticsStockScaleVO> voList = records.stream()
                .map(MonitoringStatisticsStockScaleVO::new).collect(Collectors.toList());

        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public MonitoringStatisticsStockScaleVO detailById(String id) {
        MonitoringStatisticsStockScale entity = getById(id);
        if (entity == null) {
            return null;
        }
        return new MonitoringStatisticsStockScaleVO(entity);
    }

    @Override
    public Boolean edit(MonitoringStatisticsStockScaleEditDTO editDTO) {

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
    public Boolean add(MonitoringStatisticsStockScaleAddDTO addDTO) {
        addDTO.setCreateBy(UserSecurity.userName());
        addDTO.setCreateTime(LocalDateTime.now());
        addDTO.setUpdateBy(UserSecurity.userName());
        addDTO.setUpdateTime(LocalDateTime.now());
        addDTO.setCountryId(UserSecurity.loginUser().getCountryId());
        return save(addDTO);
    }

    @Override
    public List<MonitoringStatisticsStockScaleVO> getByStatisticsId(String statisticsId) {
        MonitoringStatisticsStockScaleQueryDTO queryDTO = new MonitoringStatisticsStockScaleQueryDTO(1, Integer.MAX_VALUE);
        Page<MonitoringStatisticsStockScaleVO> page = page(queryDTO);
        return page.getRecords();
    }

    @Override
    public Boolean delete(MonitoringStatisticsStockScaleDeleteDTO deleteDTO) {
        List<String> ids = deleteDTO.getIds();

        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        return removeByIds(ids);
    }

}
