package com.sydata.monitoring.service.impl;

import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.entity.MonitoringStatisticsPurchaseSale;
import com.sydata.monitoring.service.IMonitoringStatisticsPurchaseSaleService;
import com.sydata.monitoring.vo.MonitoringStatisticsProcessingRotationVO;
import com.sydata.monitoring.vo.MonitoringStatisticsPurchaseSaleVO;
import com.sydata.monitoring.mapper.MonitoringStatisticsPurchaseSaleMapper;
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
 * 流通检测-粮油购销信息 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Service
public class MonitoringStatisticsPurchaseSaleServiceImpl extends ServiceImpl<MonitoringStatisticsPurchaseSaleMapper, MonitoringStatisticsPurchaseSale> implements IMonitoringStatisticsPurchaseSaleService {

    @Resource
    private MonitoringStatisticsPurchaseSaleMapper monitoringStatisticsPurchaseSaleMapper;


    @Override
    public Page<MonitoringStatisticsPurchaseSaleVO> page(MonitoringStatisticsPurchaseSaleQueryDTO queryDTO) {
        Page<MonitoringStatisticsPurchaseSale> page = lambdaQuery()
                .page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()));

        // 获取VO分页对象
        Page<MonitoringStatisticsPurchaseSaleVO> voPage = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize(), page.getTotal());

        List<MonitoringStatisticsPurchaseSale> records = page.getRecords();

        if (CollectionUtils.isEmpty(records)) {
            return voPage;
        }

        List<MonitoringStatisticsPurchaseSaleVO> voList = records.stream()
                .map(MonitoringStatisticsPurchaseSaleVO::new).collect(Collectors.toList());

        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public MonitoringStatisticsPurchaseSaleVO detailById(String id) {
        MonitoringStatisticsPurchaseSale entity = getById(id);
        if (entity == null) {
            return null;
        }
        return new MonitoringStatisticsPurchaseSaleVO(entity);
    }

    @Override
    public Boolean edit(MonitoringStatisticsPurchaseSaleEditDTO editDTO) {

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
    public Boolean add(MonitoringStatisticsPurchaseSaleAddDTO addDTO) {
        addDTO.setCreateBy(UserSecurity.userName());
        addDTO.setCreateTime(LocalDateTime.now());
        addDTO.setUpdateBy(UserSecurity.userName());
        addDTO.setUpdateTime(LocalDateTime.now());
        addDTO.setCountryId(UserSecurity.loginUser().getCountryId());
        return save(addDTO);
    }

    @Override
    public List<MonitoringStatisticsPurchaseSaleVO> getByStatisticsId(String statisticsId) {
        MonitoringStatisticsPurchaseSaleQueryDTO queryDTO = new MonitoringStatisticsPurchaseSaleQueryDTO(1, Integer.MAX_VALUE);
        Page<MonitoringStatisticsPurchaseSaleVO> page = page(queryDTO);
        return page.getRecords();
    }

    @Override
    public Boolean delete(MonitoringStatisticsPurchaseSaleDeleteDTO deleteDTO) {
        List<String> ids = deleteDTO.getIds();

        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        return removeByIds(ids);
    }

}
