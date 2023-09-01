package com.sydata.monitoring.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.framework.util.StringUtils;
import com.sydata.monitoring.enums.MonitoringAuditStatusEnum;
import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.entity.GoodCompany;
import com.sydata.monitoring.events.GoodCompanyAuditPassEvent;
import com.sydata.monitoring.mapper.GoodCompanyMapper;
import com.sydata.monitoring.service.IGoodCompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.monitoring.vo.GoodCompanyVO;
import com.sydata.organize.security.UserSecurity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 流通检测-好粮油企业 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-24
 */
@Service
public class GoodCompanyServiceImpl extends ServiceImpl<GoodCompanyMapper, GoodCompany> implements IGoodCompanyService {
    @Resource
    private GoodCompanyMapper goodCompanyMapper;

    @Override
    public Page<GoodCompanyVO> pagination(GoodCompanyQueryDTO queryDTO) {
        Page<GoodCompany> page = lambdaQuery()
                .eq(StringUtils.isNotBlank(queryDTO.getCompanyName()), GoodCompany::getCompanyName, queryDTO.getCompanyName())
                .page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()));

        // 获取VO分页对象
        Page<GoodCompanyVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());

        List<GoodCompany> records = page.getRecords();

        if (CollectionUtils.isEmpty(records)) {
            return voPage;
        }

        List<GoodCompanyVO> voList = records.stream()
                .map(GoodCompanyVO::new)
                .collect(Collectors.toList());

        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public GoodCompanyVO detailById(String id) {
        GoodCompany entity = getById(id);

        if (entity == null) {
            return null;
        }

        return new GoodCompanyVO(entity);
    }

    @Override
    public Boolean edit(GoodCompanyEditDTO editDTO) {
        if (editDTO.getId() == null) {
            editDTO.setPlatformRegister("已注册");
            editDTO.setRecordSource("粮食平台");
            editDTO.setRecordDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
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
    public Boolean add(GoodCompanyAddDTO addDTO) {
        addDTO.setCreateBy(UserSecurity.userName());
        addDTO.setCreateTime(LocalDateTime.now());
        addDTO.setUpdateBy(UserSecurity.userName());
        addDTO.setUpdateTime(LocalDateTime.now());
        addDTO.setCountryId(UserSecurity.loginUser().getCountryId());
        return save(addDTO);
    }

    @Override
    public Boolean aduit(GoodCompanyAuditDTO auditDTO) {

        String id = auditDTO.getId();

        GoodCompany company = getById(id);

        Assert.state(MonitoringAuditStatusEnum.PENDING_AUDIT.name().equals(company.getStatus()), "请检查审核状态");

        String status = auditDTO.getStatus();

        if (MonitoringAuditStatusEnum.PASS.name().equals(status)) {
            SpringUtil.publishEvent(new GoodCompanyAuditPassEvent(company));
        }

        return lambdaUpdate()
                .eq(GoodCompany::getId, id)
                .set(GoodCompany::getStatus, status)
                .update();
    }

    @Override
    public Boolean delete(GoodCompanyDeleteDTO deleteDTO) {
        List<String> ids = deleteDTO.getIds();

        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        return removeByIds(ids);
    }
}
