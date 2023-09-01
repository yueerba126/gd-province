package com.sydata.custody.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.service.ICargoService;
import com.sydata.basis.service.IGranaryService;
import com.sydata.basis.service.IStockHouseService;
import com.sydata.basis.service.IWarehouseService;
import com.sydata.custody.dto.GrainCustodyJobsQueryDto;
import com.sydata.custody.service.ICustodyJobService;
import com.sydata.custody.vo.GrainSteamTaskInformationVo;
import com.sydata.custody.vo.GrainVentilationJobVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.StringUtils;
import com.sydata.manage.domain.SteamTaskInformation;
import com.sydata.manage.domain.Ventilation;
import com.sydata.manage.service.ISteamTaskInformationService;
import com.sydata.manage.service.IVentilationService;
import com.sydata.manage.vo.SteamTaskInformationVo;
import com.sydata.manage.vo.VentilationVo;
import com.sydata.trade.service.IStockGrainService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangcy
 * @since 2023/4/23 14:47
 */
@Service
public class CustodyJobServiceImpl implements ICustodyJobService {

    @Resource
    private IVentilationService ventilationService;

    @Resource
    private ISteamTaskInformationService steamTaskInformationService;

    @Resource
    private IStockGrainService stockGrainService;

    @Resource
    private IStockHouseService stockHouseService;

    @Resource
    private IWarehouseService warehouseService;

    @Resource
    private IGranaryService granaryService;

    @Resource
    private ICargoService cargoService;

    @DataBindFieldConvert
    @Override
    public Page<GrainVentilationJobVo> grainVentilationPage(GrainCustodyJobsQueryDto queryDto) {
        Page<Ventilation> page = ventilationService.lambdaQuery()
                .eq(StringUtils.isNoneBlank(queryDto.getCfdm()), Ventilation::getCfdm, queryDto.getCfdm())
                .eq(StringUtils.isNoneBlank(queryDto.getKqdm()), Ventilation::getStockHouseId, queryDto.getKqdm())
                .eq(StringUtils.isNoneBlank(queryDto.getDwdm()), Ventilation::getEnterpriseId, queryDto.getDwdm())
                .likeRight(StringUtils.isNoneBlank(queryDto.getHwdm()), Ventilation::getTfzydh, queryDto.getHwdm())
                .page(new Page<>(queryDto.getPageNum(), queryDto.getPageSize()));

        List<Ventilation> records = page.getRecords();

        Page<GrainVentilationJobVo> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());

        if (CollectionUtils.isEmpty(records)) {
            return voPage;
        }

        List<GrainVentilationJobVo> voList = records.stream().map(GrainVentilationJobVo::new).collect(Collectors.toList());

        voPage.setRecords(voList);

        return voPage;
    }

    @DataBindFieldConvert
    @Override
    public GrainVentilationJobVo ventilationJobVoDetail(String id) {
        VentilationVo detail = ventilationService.detail(id);
        return new GrainVentilationJobVo(detail);
    }

    @DataBindFieldConvert
    @Override
    public Page<GrainSteamTaskInformationVo> grainSteamTaskInformationPage(GrainCustodyJobsQueryDto queryDto) {
        Page<SteamTaskInformation> page = steamTaskInformationService.lambdaQuery()
                .eq(StringUtils.isNoneBlank(queryDto.getCfdm()), SteamTaskInformation::getCfdm, queryDto.getCfdm())
                .eq(StringUtils.isNoneBlank(queryDto.getKqdm()), SteamTaskInformation::getStockHouseId, queryDto.getKqdm())
                .eq(StringUtils.isNoneBlank(queryDto.getDwdm()), SteamTaskInformation::getEnterpriseId, queryDto.getDwdm())
                .page(new Page<>(queryDto.getPageNum(), queryDto.getPageSize()));

        List<SteamTaskInformation> records = page.getRecords();

        Page<GrainSteamTaskInformationVo> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());

        if (CollectionUtils.isEmpty(records)) {
            return voPage;
        }

        List<GrainSteamTaskInformationVo> voList = records.stream().map(GrainSteamTaskInformationVo::new).collect(Collectors.toList());

        voPage.setRecords(voList);

        return voPage;
    }

    @DataBindFieldConvert
    @Override
    public GrainSteamTaskInformationVo grainSteamTaskInformationDetail(String id) {
        SteamTaskInformationVo detail = steamTaskInformationService.detail(id);
        return new GrainSteamTaskInformationVo(detail);
    }
}
