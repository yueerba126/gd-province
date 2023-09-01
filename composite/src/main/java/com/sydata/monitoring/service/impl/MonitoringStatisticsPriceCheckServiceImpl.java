package com.sydata.monitoring.service.impl;

import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.entity.MonitoringStatisticsPriceCheck;
import com.sydata.monitoring.service.IMonitoringStatisticsPriceCheckService;
import com.sydata.monitoring.vo.MonitoringStatisticsPriceCheckVO;
import com.sydata.monitoring.mapper.MonitoringStatisticsPriceCheckMapper;
import com.sydata.organize.security.UserSecurity;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

/**
 * <p>
 * 流通检测-价格检测信息 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-26
 */
@Service
public class MonitoringStatisticsPriceCheckServiceImpl extends ServiceImpl<MonitoringStatisticsPriceCheckMapper, MonitoringStatisticsPriceCheck> implements IMonitoringStatisticsPriceCheckService {

    @Resource
    private MonitoringStatisticsPriceCheckMapper monitoringStatisticsPriceCheckMapper;


    @Override
    public Page<MonitoringStatisticsPriceCheckVO> page(MonitoringStatisticsPriceCheckQueryDTO queryDTO) {
        Page<MonitoringStatisticsPriceCheck> page = lambdaQuery()
                .page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()));

        // 获取VO分页对象
        Page<MonitoringStatisticsPriceCheckVO> voPage = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize(), page.getTotal());

        List<MonitoringStatisticsPriceCheck> records = page.getRecords();

        if (CollectionUtils.isEmpty(records)) {
            return voPage;
        }

        List<MonitoringStatisticsPriceCheckVO> voList = records.stream()
                .map(MonitoringStatisticsPriceCheckVO::new).collect(Collectors.toList());

        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public MonitoringStatisticsPriceCheckVO detailById(String id) {
        MonitoringStatisticsPriceCheck entity = getById(id);
        if (entity == null) {
            return null;
        }
        return new MonitoringStatisticsPriceCheckVO(entity);
    }

    @Override
    public Boolean edit(MonitoringStatisticsPriceCheckEditDTO editDTO) {
        editDTO.setIncomingPrice(Optional.ofNullable(editDTO.getIncomingPrice()).orElse(BigDecimal.ZERO));
        editDTO.setFactoryPrice(Optional.ofNullable(editDTO.getFactoryPrice()).orElse(BigDecimal.ZERO));
        editDTO.setRetailPrice(Optional.ofNullable(editDTO.getRetailPrice()).orElse(BigDecimal.ZERO));
        editDTO.setSalePrice(Optional.ofNullable(editDTO.getSalePrice()).orElse(BigDecimal.ZERO));
        editDTO.setTradePrice(Optional.ofNullable(editDTO.getTradePrice()).orElse(BigDecimal.ZERO));

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
    public Boolean add(MonitoringStatisticsPriceCheckAddDTO addDTO) {
        Long countMaterialType = lambdaQuery()
                .eq(MonitoringStatisticsPriceCheck::getMaterialType, addDTO.getMaterialType())
                .eq(MonitoringStatisticsPriceCheck::getStatisticId, addDTO.getStatisticId())
                .count();

        Assert.state(countMaterialType <= 0, "品种已经存在，请勿重复配置");

        addDTO.setIncomingPrice(Optional.ofNullable(addDTO.getIncomingPrice()).orElse(BigDecimal.ZERO));
        addDTO.setFactoryPrice(Optional.ofNullable(addDTO.getFactoryPrice()).orElse(BigDecimal.ZERO));
        addDTO.setRetailPrice(Optional.ofNullable(addDTO.getRetailPrice()).orElse(BigDecimal.ZERO));
        addDTO.setSalePrice(Optional.ofNullable(addDTO.getSalePrice()).orElse(BigDecimal.ZERO));
        addDTO.setTradePrice(Optional.ofNullable(addDTO.getTradePrice()).orElse(BigDecimal.ZERO));
        addDTO.setCreateBy(UserSecurity.userName());
        addDTO.setCreateTime(LocalDateTime.now());
        addDTO.setUpdateBy(UserSecurity.userName());
        addDTO.setUpdateTime(LocalDateTime.now());
        addDTO.setCountryId(UserSecurity.loginUser().getCountryId());
        return save(addDTO);
    }

    @Override
    public List<MonitoringStatisticsPriceCheckVO> getByStatisticsId(String statisticsId) {
        MonitoringStatisticsPriceCheckQueryDTO queryDTO = new MonitoringStatisticsPriceCheckQueryDTO(1, Integer.MAX_VALUE);
        Page<MonitoringStatisticsPriceCheckVO> page = page(queryDTO);
        return page.getRecords();
    }

    @Override
    public List<MonitoringStatisticsPriceCheckVO> selectByStatisticsIds(Set<String> statisticsIds) {

        if (CollectionUtils.isEmpty(statisticsIds)) {
            return Collections.emptyList();
        }

        return lambdaQuery()
                .in(MonitoringStatisticsPriceCheck::getStatisticId, statisticsIds)
                .list()
                .stream()
                .map(MonitoringStatisticsPriceCheckVO::new)
                .collect(Collectors.toList());
    }


    @Override
    public Boolean delete(MonitoringStatisticsPriceCheckDeleteDTO deleteDTO) {
        List<String> ids = deleteDTO.getIds();

        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        return removeByIds(ids);
    }

}
