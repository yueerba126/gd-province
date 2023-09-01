package com.sydata.monitoring.service.impl;

import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.StringUtils;
import com.sydata.monitoring.enums.MonitoringAuditStatusEnum;
import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.entity.GoodProduceCompany;
import com.sydata.monitoring.mapper.GoodProduceCompanyMapper;
import com.sydata.monitoring.service.IGoodProduceCompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.organize.security.UserSecurity;
import org.springframework.stereotype.Service;
import com.sydata.monitoring.vo.GoodProduceCompanyVO;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

/**
 * <p>
 * 流通检测-放心粮油企业 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-24
 */
@Service
public class GoodProduceCompanyServiceImpl extends ServiceImpl<GoodProduceCompanyMapper, GoodProduceCompany> implements IGoodProduceCompanyService {

    @Resource
    private GoodProduceCompanyMapper goodProduceCompanyMapper;

    @DataBindFieldConvert
    @Override
    public Page<GoodProduceCompanyVO> pagination(GoodProduceCompanyQueryDTO queryDTO) {
        Page<GoodProduceCompany> page = lambdaQuery()
                .eq(StringUtils.isNotBlank(queryDTO.getCompanyName()), GoodProduceCompany::getCompanyName, queryDTO.getCompanyName())
                .page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()));

        // 获取VO分页对象
        Page<GoodProduceCompanyVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());

        List<GoodProduceCompany> records = page.getRecords();

        if (CollectionUtils.isEmpty(records)) {
            return voPage;
        }

        List<GoodProduceCompanyVO> voList = records.stream()
                .map(GoodProduceCompanyVO::new)
                .collect(Collectors.toList());

        voPage.setRecords(voList);

        return voPage;
    }

    @DataBindFieldConvert
    @Override
    public GoodProduceCompanyVO detailById(String id) {
        GoodProduceCompany entity = getById(id);
        if (entity == null) {
            return null;
        }
        return new GoodProduceCompanyVO(entity);
    }

    @Override
    public Boolean edit(GoodProduceCompanyEditDTO editDTO) {
        if (editDTO.getId() == null) {
            editDTO.setStatus(MonitoringAuditStatusEnum.PENDING_AUDIT.name());
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
    public Boolean add(GoodProduceCompanyAddDTO addDTO) {
        addDTO.setStatus(MonitoringAuditStatusEnum.PENDING_AUDIT.name());
        addDTO.setCreateBy(UserSecurity.userName());
        addDTO.setCreateTime(LocalDateTime.now());
        addDTO.setUpdateBy(UserSecurity.userName());
        addDTO.setUpdateTime(LocalDateTime.now());
        addDTO.setCountryId(UserSecurity.loginUser().getCountryId());
        return save(addDTO);
    }

    @Override
    public Boolean audit(GoodProduceCompanyAuditDTO auditDTO) {
        String id = auditDTO.getId();

        GoodProduceCompany company = getById(id);

        Assert.state(MonitoringAuditStatusEnum.PENDING_AUDIT.name().equals(company.getStatus()), "请检查审核状态");

        return lambdaUpdate()
                .eq(GoodProduceCompany::getId, id)
                .set(GoodProduceCompany::getStatus, auditDTO.getStatus())
                .update();
    }

    @Override
    public Boolean delete(GoodProduceCompanyDeleteDTO deleteDTO) {
        List<String> ids = deleteDTO.getIds();

        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        return removeByIds(ids);
    }

}
